package fr.unilim.iut.shi_fou_mi.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fr.unilim.iut.shi_fou_mi.connectivity.BluetoothClient
import fr.unilim.iut.shi_fou_mi.connectivity.BluetoothGameManager
import fr.unilim.iut.shi_fou_mi.connectivity.BluetoothServer
import fr.unilim.iut.shi_fou_mi.logic.Weapon
import fr.unilim.iut.shi_fou_mi.logic.games.ClassicGameLogic
import fr.unilim.iut.shi_fou_mi.logic.games.GamesLogic
import fr.unilim.iut.shi_fou_mi.logic.games.StrategicGameLogic
import fr.unilim.iut.shi_fou_mi.logic.strategies.AdaptiveStrategy
import fr.unilim.iut.shi_fou_mi.logic.strategies.ComputerStrategy
import fr.unilim.iut.shi_fou_mi.logic.strategies.HumanStrategy
import fr.unilim.iut.shi_fou_mi.logic.strategies.Strategies
import fr.unilim.iut.shi_fou_mi.logic.weapons.WeaponRepository
import fr.unilim.iut.shi_fou_mi.ui.screens.ChooseNameScreen
import fr.unilim.iut.shi_fou_mi.ui.screens.ComputerStrategySelectionScreen
import fr.unilim.iut.shi_fou_mi.ui.screens.GameModeSelectionScreen
import fr.unilim.iut.shi_fou_mi.ui.screens.HomeScreen
import fr.unilim.iut.shi_fou_mi.ui.screens.LinkScreen
import fr.unilim.iut.shi_fou_mi.ui.screens.MultiplayerScreen
import fr.unilim.iut.shi_fou_mi.ui.screens.OpponentSelectionScreen
import fr.unilim.iut.shi_fou_mi.ui.screens.PlayScreen
import fr.unilim.iut.shi_fou_mi.ui.screens.PlayerStrategySelectionScreen
import fr.unilim.iut.shi_fou_mi.ui.screens.ScoresScreen
import fr.unilim.iut.shi_fou_mi.ui.screens.WaitingScreen
import kotlinx.coroutines.flow.MutableStateFlow

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AppNavigation(bluetoothGameManager: BluetoothGameManager) {
    val navController = rememberNavController()
    var bluetoothServer by remember { mutableStateOf<BluetoothServer?>(null) }
    var bluetoothClient by remember { mutableStateOf<BluetoothClient?>(null) }
    var opponentWeapon = MutableStateFlow<Weapon?>(null)
    var playerWeapon = MutableStateFlow<Weapon?>(null)
    var oldPlayerWeapon by remember { mutableStateOf<Weapon?>(null) }

    val opponentWeaponState = opponentWeapon.collectAsState()
    val playerWeaponState = playerWeapon.collectAsState()



    NavHost(navController = navController, startDestination = "home") {
        fun onReceivedMessage(playerName: String, message: String) {
            println("Received message: $message")
            if (message.startsWith("name:")) {
                val oppenentName = message.substringAfter("name:")

                navController.navigate("multiplayer/$playerName/$oppenentName")
            } else if (message.startsWith("weapon:")) {
                val weapon = message.substringAfter("weapon:")
                for (wep in WeaponRepository.weapons) {
                    if (weapon == wep.javaClass.simpleName) {
                        opponentWeapon.value = wep
                    }
                }
            }
        }

        composable("home") {
            HomeScreen(navController)
        }

        composable("scores") {
            ScoresScreen(navController)
        }
        composable("choosename") {
            ChooseNameScreen { playerName ->
                navController.navigate("opponent/$playerName")
            }
        }
        composable(
            "opponent/{playerName}",
            arguments = listOf(navArgument("playerName") { type = NavType.StringType })
        ) { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: ""
            OpponentSelectionScreen(playerName) { opponent ->
                if (opponent == "PLAYER") {

                    navController.navigate("link/$playerName")
                } else if (opponent == "HOST") {
                    navController.navigate("waitingScreen/$playerName")
                } else {
                    navController.navigate("gamemode/$playerName/$opponent")
                }
            }
        }

        composable(
            "link/{playerName}",
            arguments = listOf(
                navArgument("playerName") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: ""
            val devices = bluetoothGameManager.getBluetoothDevices()
            LinkScreen(playerName, devices) { opponent ->
                bluetoothClient =
                    BluetoothClient(bluetoothGameManager.bluetoothAdapter!!, opponent, playerName) {
                        onReceivedMessage(playerName, it)
                    }
                bluetoothClient!!.start()
            }
        }

        composable(
            "waitingScreen/{playerName}",
            arguments = listOf(
                navArgument("playerName") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: ""
            if (bluetoothServer == null) {

                bluetoothServer =
                    BluetoothServer(bluetoothGameManager.bluetoothAdapter!!, playerName) {
                        onReceivedMessage(playerName, it)
                    }
                bluetoothServer!!.start()

            }
            WaitingScreen()
        }


        composable(
            "gamemode/{playerName}/{opponent}",
            arguments = listOf(
                navArgument("playerName") { type = NavType.StringType },
                navArgument("opponent") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: ""
            val opponent = backStackEntry.arguments?.getString("opponent") ?: ""
            GameModeSelectionScreen(playerName, opponent) { gameMode ->
                navController.navigate("playerstrategy/$playerName/$opponent/$gameMode")
                if (gameMode == GamesLogic.CLASSIC.toString()) {
                    navController.navigate("play/$playerName/$opponent/$gameMode/${Strategies.HUMAN}/${Strategies.COMPUTER}")
                } else {
                    navController.navigate("playerstrategy/$playerName/$opponent/$gameMode")
                }
            }
        }

        composable(
            "playerstrategy/{playerName}/{opponent}/{gameMode}",
            arguments = listOf(
                navArgument("playerName") { type = NavType.StringType },
                navArgument("opponent") { type = NavType.StringType },
                navArgument("gameMode") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: ""
            val opponent = backStackEntry.arguments?.getString("opponent") ?: ""
            val gameMode = backStackEntry.arguments?.getString("gameMode") ?: ""
            PlayerStrategySelectionScreen(playerName, opponent, gameMode) { playerStrategy ->
                navController.navigate("computerstrategy/$playerName/$opponent/$gameMode/$playerStrategy")
            }
        }
        composable(
            "computerstrategy/{playerName}/{opponent}/{gameMode}/{playerStrategy}",
            arguments = listOf(
                navArgument("playerName") { type = NavType.StringType },
                navArgument("opponent") { type = NavType.StringType },
                navArgument("gameMode") { type = NavType.StringType },
                navArgument("playerStrategy") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: ""
            val opponent = backStackEntry.arguments?.getString("opponent") ?: ""
            val gameMode = backStackEntry.arguments?.getString("gameMode") ?: ""
            val playerStrategy = backStackEntry.arguments?.getString("playerStrategy") ?: ""
            ComputerStrategySelectionScreen(
                playerName,
                opponent,
                gameMode,
                playerStrategy
            ) { computerStrategy ->
                navController.navigate("play/$playerName/$opponent/$gameMode/$playerStrategy/$computerStrategy")
            }
        }
        composable(
            "play/{playerName}/{opponent}/{gameMode}/{playerStrategy}/{computerStrategy}",
            arguments = listOf(
                navArgument("playerName") { type = NavType.StringType },
                navArgument("opponent") { type = NavType.StringType },
                navArgument("gameMode") { type = NavType.StringType },
                navArgument("playerStrategy") { type = NavType.StringType },
                navArgument("computerStrategy") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val player = backStackEntry.arguments?.getString("playerName") ?: ""
            val gameLogic = remember {
                when (backStackEntry.arguments?.getString("gameMode") ?: "") {
                    GamesLogic.STRATEGIC.toString() -> StrategicGameLogic()
                    else -> ClassicGameLogic()
                }
            }
            val playerStrategy = remember {
                when (backStackEntry.arguments?.getString("playerStrategy") ?: "") {
                    Strategies.HUMAN.toString() -> HumanStrategy()
                    Strategies.COMPUTER.toString() -> ComputerStrategy()
                    else -> AdaptiveStrategy()
                }
            }
            val computerStrategy = remember {
                when (backStackEntry.arguments?.getString("computerStrategy") ?: "") {
                    Strategies.HUMAN.toString() -> HumanStrategy()
                    Strategies.COMPUTER.toString() -> ComputerStrategy()
                    else -> AdaptiveStrategy()
                }
            }

            PlayScreen(
                navController = navController,
                gameLogic = gameLogic,
                player = player,
                playerStrategy = playerStrategy,
                computerStrategy = computerStrategy
            )

        }
        composable("multiplayer/{playerName}/{opponentName}") { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: ""
            val opponentName = backStackEntry.arguments?.getString("opponentName") ?: ""

            if (playerWeaponState.value != oldPlayerWeapon) {
                if (bluetoothClient != null) {
                    bluetoothClient?.sendMessage("weapon:${playerWeaponState.value?.javaClass?.simpleName}")
                }
                if (bluetoothServer != null) {
                    bluetoothServer?.sendMessage("weapon:${playerWeaponState.value?.javaClass?.simpleName}")
                }
                oldPlayerWeapon = playerWeapon.value
            }

            MultiplayerScreen(
                playerWeapon,
                opponentWeapon,
                playerName,
                opponentName
            )

        }

    }
}
