package fr.unilim.iut.shi_fou_mi.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.unilim.iut.shi_fou_mi.logic.Weapon
import fr.unilim.iut.shi_fou_mi.ui.components.Screen
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun MultiplayerScreen(
    playerWeaponState: MutableStateFlow<Weapon?>,
    opponentWeaponState: MutableStateFlow<Weapon?>,
    playerName: String,
    opponentName: String
) {
    val playerWeapon = playerWeaponState.collectAsState()
    val opponentWeapon = opponentWeaponState.collectAsState()

    if (playerWeapon.value == null) {
        ChooseWeaponClassScreen {
            playerWeaponState.value = it
            println("sidhgqlsdfhg")
        }
    } else if (opponentWeapon.value == null) {
        WaitingScreen()
    } else {
        Screen {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = playerWeapon.value!!.getDrawableResource(true)),
                    contentDescription = "hand left player",
                    modifier = Modifier
                        .size(250.dp)
                        .absoluteOffset(x = (-40).dp, y = (-230).dp),
                )
                Image(
                    painter = painterResource(id = opponentWeapon.value!!.getDrawableResource(false)),
                    contentDescription = "hand right player",
                    modifier = Modifier
                        .size(250.dp)
                        .absoluteOffset(x = (40).dp, y = (-230).dp)
                )

            }
        }
    }


}