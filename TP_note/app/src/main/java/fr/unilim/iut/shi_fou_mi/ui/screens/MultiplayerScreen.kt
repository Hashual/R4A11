package fr.unilim.iut.shi_fou_mi.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.unilim.iut.shi_fou_mi.logic.Weapon
import fr.unilim.iut.shi_fou_mi.ui.components.Screen
import fr.unilim.iut.shi_fou_mi.utils.LanguageManager
import fr.unilim.iut.shi_fou_mi.utils.capitalizeFirstLetter
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun MultiplayerScreen(
    navController: NavController,
    playerWeaponState: MutableStateFlow<Weapon?>,
    opponentWeaponState: MutableStateFlow<Weapon?>,
    playerName: String,
    opponentName: String
) {
    val playerWeapon = playerWeaponState.collectAsState()
    val opponentWeapon = opponentWeaponState.collectAsState()
    val rotation = remember{Animatable(0f)}
    var scoreText = ""

    if (playerWeapon.value != null && opponentWeapon.value != null) {
        if (playerWeapon.value!!.fightAgainst(opponentWeapon.value!!) == 1) {
            scoreText = " $playerName ${LanguageManager.getLexicon().win} !"
        } else if (playerWeapon.value!!.fightAgainst(opponentWeapon.value!!) == -1) {
            scoreText = "$opponentName ${LanguageManager.getLexicon().win} !"
        } else {
            scoreText = LanguageManager.getLexicon().draw.capitalizeFirstLetter()
        }
    }

    if (playerWeapon.value == null) {
        ChooseWeaponClassScreen {
            playerWeaponState.value = it
        }
    } else if (opponentWeapon.value == null) {
        WaitingScreen()
    } else {
        fightScreen(
            navController = navController,
            leftHandWeapon = playerWeapon.value!!,
            rightHandWeapon = opponentWeapon.value!!,
            playerName = playerName,
            opponentName = opponentName,
            rotation = rotation,
            onPlayedButtonClicked = {
                playerWeaponState.value = null
                opponentWeaponState.value = null
            },
            playButtonDisaibled = false,
            scoreText = scoreText,
            customText = null,
            showFireworks = null

        )
    }



}