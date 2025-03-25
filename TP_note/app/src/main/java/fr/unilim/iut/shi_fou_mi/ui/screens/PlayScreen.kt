package fr.unilim.iut.shi_fou_mi.ui.screens

import androidx.compose.animation.core.Animatable
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
import fr.unilim.iut.shi_fou_mi.logic.Player
import fr.unilim.iut.shi_fou_mi.logic.Strategy
import fr.unilim.iut.shi_fou_mi.logic.Weapon
import fr.unilim.iut.shi_fou_mi.logic.weapons.Rock
import fr.unilim.iut.shi_fou_mi.sensors.Gyroscope
import fr.unilim.iut.shi_fou_mi.ui.components.CustomButton
import fr.unilim.iut.shi_fou_mi.ui.components.CustomText
import fr.unilim.iut.shi_fou_mi.ui.components.Screen
import fr.unilim.iut.shi_fou_mi.ui.components.TextBox
import fr.unilim.iut.shi_fou_mi.utils.LanguageManager
import fr.unilim.iut.shi_fou_mi.utils.capitalizeFirstLetter
import kotlinx.coroutines.launch

@Composable
fun PlayScreen(
    navController: NavController,
    gameLogic: GameLogic,
    player: Player,
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


    val scoreText = remember { mutableStateOf("") }

    fun updateScoreText() {
        if (leftHandWeapon.value.fightAgainst(rightHandWeapon.value) == 1) {
            player.incrementScore()
            scoreText.value = " ${player.name} ${LanguageManager.getLexicon().win} !"
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
            val (leftWeapon, rightWeapon) = gameLogic.launchRound(playerStrategy, computerStrategy, playerHistory, computerHistory)
            leftHandWeapon.value = leftWeapon
            rightHandWeapon.value = rightWeapon
            playerHistory.add(leftWeapon)
            computerHistory.add(rightWeapon)
            updateScoreText()
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

    Screen {
        Image(
            painter = painterResource(id = leftHandWeapon.value.getDrawableResource(true)),
            contentDescription = "hand left player",
            modifier = Modifier
                .size(250.dp)
                .graphicsLayer(rotationZ = -rotation.value)
                .align(Alignment.BottomStart)
                .absoluteOffset(x = (-40).dp, y = (-230).dp) ,
        )
        Image(
            painter = painterResource(id = rightHandWeapon.value.getDrawableResource(false)),
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
                .absoluteOffset(x = 25.dp, y= (-210).dp)
        ) {
            TextBox("J1", 50)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.BottomEnd)
                .absoluteOffset(x = (-20).dp, y= (-210).dp)
        ) {
            TextBox("\uD83E\uDD16", 50)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.TopCenter)
                .absoluteOffset(y = (0.20f * LocalConfiguration.current.screenHeightDp).dp)
        ) {
            CustomText(scoreText.value, 35.sp, TextAlign.Center)
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
                onClick = { launchRound() },
                text = LanguageManager.getLexicon().play.uppercase(),
                padV = 10,
                width = 100,
                textSize = 14,
                isDesactivated = isPlayBtnDesactivated.value
            )
        }
    }
}