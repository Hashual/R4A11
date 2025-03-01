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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.unilim.iut.shi_fou_mi.R
import fr.unilim.iut.shi_fou_mi.logic.GameLogic
import fr.unilim.iut.shi_fou_mi.logic.GameMode
import fr.unilim.iut.shi_fou_mi.logic.Weapon
import fr.unilim.iut.shi_fou_mi.logic.games.ClassicGameLogic
import fr.unilim.iut.shi_fou_mi.logic.weapons.Rock
import fr.unilim.iut.shi_fou_mi.sensors.Gyroscope
import fr.unilim.iut.shi_fou_mi.ui.components.CustomButton
import fr.unilim.iut.shi_fou_mi.ui.components.CustomText
import fr.unilim.iut.shi_fou_mi.ui.components.TextBox
import kotlinx.coroutines.launch

@Composable
fun PlayScreen(navController: NavController, mode: GameMode = GameMode.CLASSIC) {
    val context = LocalContext.current

    val rotation = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    val leftHandWeapon = remember { mutableStateOf<Weapon>(Rock()) }
    val rightHandWeapon = remember { mutableStateOf<Weapon>(Rock()) }

    val scoreText = remember { mutableStateOf("") }

    val gameLogic: GameLogic = remember(mode) {
        when (mode) {
            GameMode.CLASSIC -> ClassicGameLogic()
        }
    }

    fun updateScoreText() {
        if (leftHandWeapon.value.fightAgainst(rightHandWeapon.value) == 1) {
            scoreText.value = "Le joueur gagne !"
        } else if (leftHandWeapon.value.fightAgainst(rightHandWeapon.value) == -1) {
            scoreText.value = "Le robot gagne !"
        } else {
            scoreText.value = "Match nul"
        }
    }

    fun launchRound() {
        scoreText.value = ""
        coroutineScope.launch {
            repeat(3) {
                rotation.animateTo(0f, animationSpec = tween(100))
                rotation.animateTo(10f, animationSpec = tween(100))
            }
            rotation.animateTo(0f, animationSpec = tween(100))
            val (leftWeapon, rightWeapon) = gameLogic.launchRound()
            leftHandWeapon.value = leftWeapon
            rightHandWeapon.value = rightWeapon
            updateScoreText()
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

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_game),
            contentDescription = "background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Image(
            painter = painterResource(id = leftHandWeapon.value.getDrawableResource(true)),
            contentDescription = "hand left player",
            modifier = Modifier
                .size(250.dp)
                .graphicsLayer(rotationZ = -rotation.value)
                .align(Alignment.BottomStart)
                .absoluteOffset(x = (-40).dp, y = (-230).dp)
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

        // "O" à 70% en hauteur et proche du bord droit
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
                onClick = { navController.popBackStack() },
                text = "RETOUR",
                padV = 10,
                width = 100,
                textSize = 14
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomButton(
                onClick = { launchRound() },
                text = "JOUER",
                padV = 10,
                width = 100,
                textSize = 14
            )
        }
    }
}