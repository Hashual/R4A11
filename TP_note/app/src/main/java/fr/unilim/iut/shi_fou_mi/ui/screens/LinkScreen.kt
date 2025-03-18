package fr.unilim.iut.shi_fou_mi.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import fr.unilim.iut.shi_fou_mi.utils.LanguageManager

@Composable
fun LinkScreen(playerName: String, onOpponentSelected: (String) -> Unit) {
    val devices = mutableListOf("Player 1", "Player 2", "Player 3", "Player 4", "Player 5", "Player 6", "Player 7", "Player 8", "Player 9", "Player 10")
    Screen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            CustomText(LanguageManager.getLexicon().chooseOpponent, 35.sp, TextAlign.Center)
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (LocalConfiguration.current.screenHeightDp * 0.485).dp)
                .size(400.dp)
                .verticalScroll(rememberScrollState())
        ) {
            devices.forEach {
                CustomButton(
                    onClick = { onOpponentSelected(it) },
                    text = it,
                    padV = 16,
                    width = 200,
                    textSize = 24
                )
                Spacer(modifier = Modifier.height(30.dp))
            }


//            CustomButton(
//                onClick = { onOpponentSelected(Opponents.COMPUTER.toString()) },
//                text = LanguageManager.getLexicon().computer.uppercase(),
//                padV = 16,
//                width = 200,
//                textSize = 24
//            )
//            Spacer(modifier = Modifier.height(30.dp))

        }
    }
}