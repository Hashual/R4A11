package fr.unilim.iut.shi_fou_mi.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.unilim.iut.shi_fou_mi.logic.Opponents
import fr.unilim.iut.shi_fou_mi.ui.components.CustomButton
import fr.unilim.iut.shi_fou_mi.ui.components.CustomText
import fr.unilim.iut.shi_fou_mi.ui.components.Screen

@Composable
fun OpponentSelectionScreen(playerName: String, onOpponentSelected: (String) -> Unit) {
    Screen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            CustomText("Choisissez votre adversaire", 35.sp, TextAlign.Center)
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (LocalConfiguration.current.screenHeightDp * 0.485).dp)
        ) {
            CustomButton(
                onClick = { onOpponentSelected(Opponents.COMPUTER.toString()) },
                text = "ORDINATEUR",
                padV = 16,
                width = 200,
                textSize = 24
            )
            Spacer(modifier = Modifier.height(30.dp))
            CustomButton(
                onClick = { onOpponentSelected(Opponents.PLAYER.toString()) },
                text = "JOUEUR",
                padV = 16,
                width = 200,
                textSize = 24,
                isDesactivated = true
            )
        }
    }
}