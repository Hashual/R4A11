package fr.unilim.iut.shi_fou_mi.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.unilim.iut.shi_fou_mi.logic.GameLogic
import fr.unilim.iut.shi_fou_mi.logic.Opponents
import fr.unilim.iut.shi_fou_mi.logic.Scores
import fr.unilim.iut.shi_fou_mi.logic.Strategy
import fr.unilim.iut.shi_fou_mi.logic.Weapon
import fr.unilim.iut.shi_fou_mi.logic.weapons.Rock
import fr.unilim.iut.shi_fou_mi.sensors.Gyroscope
import fr.unilim.iut.shi_fou_mi.ui.components.CustomButton
import fr.unilim.iut.shi_fou_mi.ui.components.CustomText
import fr.unilim.iut.shi_fou_mi.ui.components.Fireworks
import fr.unilim.iut.shi_fou_mi.ui.components.Screen
import fr.unilim.iut.shi_fou_mi.ui.components.TextBox
import fr.unilim.iut.shi_fou_mi.utils.LanguageManager
import fr.unilim.iut.shi_fou_mi.utils.capitalizeFirstLetter
import fr.unilim.iut.shi_fou_mi.utils.playNewTopPlayerSound
import fr.unilim.iut.shi_fou_mi.utils.vibrate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PlayScreen(
    navController: NavController,
    gameLogic: GameLogic,
    player: String,
    playerStrategy: Strategy? = null,
    computerStrategy: Strategy? = null
) {
    val context = LocalContext.current

    val rotation = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    val isPlayBtnDesactivated = remember { mutableStateOf(false) }

    val leftHandWeapon = remember { mutableStateOf<Weapon>(Rock()) }
    val rightHandWeapon = remember { mutableStateOf<Weapon>(Rock()) }

    val playerHistory = mutableListOf<Weapon>()
    val computerHistory = mutableListOf<Weapon>()

    val scores = Scores(LocalContext.current)
    val scoreText = remember { mutableStateOf("") }

    val topRankedPlayer = remember { scores.getFirstPlayer()?.let { mutableStateOf(it.first) } }
    val leaderText = remember { mutableStateOf("") }
    val showFireworks = remember { mutableStateOf(false) }

    fun updateLeaderText() {
        val newTopPlayer = scores.getFirstPlayer()?.first
        if (topRankedPlayer != null && newTopPlayer != null && newTopPlayer != topRankedPlayer.value) {
            leaderText.value = LanguageManager.getLexicon().isTopLeadingBoard.replace("{}", newTopPlayer)
            topRankedPlayer.value = newTopPlayer
            vibrate(context)
            playNewTopPlayerSound(context)
            showFireworks.value = true

            coroutineScope.launch {
                delay(4000)
                leaderText.value = ""
            }
        }
    }

    fun updateScoreText() {
        if (leftHandWeapon.value.fightAgainst(rightHandWeapon.value) == 1) {
            scores.updateScore(player)
            scoreText.value = " $player ${LanguageManager.getLexicon().win} !"
        } else if (leftHandWeapon.value.fightAgainst(rightHandWeapon.value) == -1) {
            scoreText.value = "Mr. Robot ${LanguageManager.getLexicon().win} !"
        } else {
            scoreText.value = LanguageManager.getLexicon().draw.capitalizeFirstLetter()
        }
    }

    fun launchRound() {
        scoreText.value = ""
        isPlayBtnDesactivated.value = true
        coroutineScope.launch {
            repeat(3) {
                rotation.animateTo(0f, animationSpec = tween(100))
                rotation.animateTo(10f, animationSpec = tween(100))
            }
            rotation.animateTo(0f, animationSpec = tween(100))
            val (leftWeapon, rightWeapon) = gameLogic.launchRound(
                playerStrategy,
                computerStrategy,
                playerHistory,
                computerHistory
            )
            leftHandWeapon.value = leftWeapon
            rightHandWeapon.value = rightWeapon
            playerHistory.add(leftWeapon)
            computerHistory.add(rightWeapon)
            updateScoreText()
            updateLeaderText()
            isPlayBtnDesactivated.value = false
        }
    }

    val gyroscope = remember {
        Gyroscope(context) {
            launchRound()
        }
    }

    androidx.compose.runtime.DisposableEffect(Unit) {
        gyroscope.start()
        onDispose { gyroscope.stop() }
    }

    fightScreen(
        navController,
        leftHandWeapon.value,
        rightHandWeapon.value,
        player,
        "\uD83E\uDD16",
        rotation,
        { launchRound() },
        isPlayBtnDesactivated.value,
        scoreText.value,
        leaderText.value
    )
}

@Composable
fun fightScreen(
    navController: NavController,
    leftHandWeapon: Weapon,
    rightHandWeapon: Weapon,
    playerName: String,
    opponentName: String,
    rotation: Animatable<Float, AnimationVector1D>,
    onPlayedButtonClicked: () -> Unit,
    playButtonDisaibled: Boolean,
    scoreText: String?,
    customText: String?
) {
    Screen {
        if (showFireworks.value) {
            Fireworks(onAnimationEnd = { showFireworks.value = false })
        }
        Image(
            painter = painterResource(id = leftHandWeapon.getDrawableResource(true)),
            contentDescription = "hand left player",
            modifier = Modifier
                .size(250.dp)
                .graphicsLayer(rotationZ = -rotation.value)
                .align(Alignment.BottomStart)
                .absoluteOffset(x = (-40).dp, y = (-230).dp),
        )
        Image(
            painter = painterResource(id = rightHandWeapon.getDrawableResource(false)),
            contentDescription = "hand right player",
            modifier = Modifier
                .size(250.dp)
                .graphicsLayer(rotationZ = rotation.value)
                .align(Alignment.BottomEnd)
                .absoluteOffset(x = (40).dp, y = (-230).dp)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.BottomStart)
                .absoluteOffset(x = 25.dp, y = (-210).dp)
        ) {
            TextBox(playerName, 50)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.BottomEnd)
                .absoluteOffset(x = (-20).dp, y = (-210).dp)
        ) {
            TextBox(opponentName, 50)
        }

        if (scoreText != null && scoreText.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.TopCenter)
                    .absoluteOffset(y = (0.20f * LocalConfiguration.current.screenHeightDp).dp)
            ) {
                CustomText(scoreText, 35.sp, TextAlign.Center)
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            CustomButton(
                onClick = { navController.navigate("home") },
                text = LanguageManager.getLexicon().back.uppercase(),
                padV = 10,
                width = 100,
                textSize = 14
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomButton(
                onClick = { onPlayedButtonClicked() },
                text = LanguageManager.getLexicon().play.uppercase(),
                padV = 10,
                width = 100,
                textSize = 14,
                isDesactivated = playButtonDisaibled
            )
            if (customText != null) {
                Spacer(modifier = Modifier.height(16.dp))
                CustomText(customText, 20.sp, TextAlign.Center)
            }
        }
    }
}