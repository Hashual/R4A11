package fr.unilim.iut.shi_fou_mi.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.unilim.iut.shi_fou_mi.ui.screens.ComputerStrategySelectionScreen
import fr.unilim.iut.shi_fou_mi.ui.screens.GameModeSelectionScreen
import fr.unilim.iut.shi_fou_mi.ui.screens.HomeScreen
import fr.unilim.iut.shi_fou_mi.ui.screens.OpponentSelectionScreen
import fr.unilim.iut.shi_fou_mi.ui.screens.PlayScreen
import fr.unilim.iut.shi_fou_mi.ui.screens.PlayerStrategySelectionScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("opponent") {
            OpponentSelectionScreen(navController = navController)
        }
        composable("gamemode") {
            GameModeSelectionScreen(navController = navController)
        }
        composable("playerstrategy") {
            PlayerStrategySelectionScreen(navController = navController)
        }
        composable("computerstrategy") {
            ComputerStrategySelectionScreen(navController = navController)
        }
        composable("play") {
            PlayScreen(navController = navController)
        }
    }
}