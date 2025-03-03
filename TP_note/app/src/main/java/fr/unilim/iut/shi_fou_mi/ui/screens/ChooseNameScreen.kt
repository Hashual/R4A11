package fr.unilim.iut.shi_fou_mi.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.unilim.iut.shi_fou_mi.ui.components.CustomButton
import fr.unilim.iut.shi_fou_mi.ui.components.CustomText
import fr.unilim.iut.shi_fou_mi.ui.components.CustomTextField
import fr.unilim.iut.shi_fou_mi.ui.components.Screen

@Composable
fun ChooseNameScreen(onPlayerNameEntered: (String) -> Unit) {
    var playerName by remember { mutableStateOf("") }
    val regex = Regex("^[A-Za-z]{1,7}$")

    Screen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            CustomText("Choisissez votre pseudo (max 7 car.)", 35.sp, TextAlign.Center)
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (LocalConfiguration.current.screenHeightDp * 0.485).dp)
        ) {
            CustomTextField(
                value = playerName,
                onValueChange = { newText ->
                    playerName = newText
                },
                placeholder = "Pseudo [a-Z]",
            )

            Spacer(modifier = Modifier.height(50.dp))

            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                CustomButton(
                    onClick = { onPlayerNameEntered(playerName) },
                    text = "JOUER",
                    padV = 16,
                    padH = 32,
                    textSize = 24,
                    isDesactivated = playerName.matches(regex).not()
                )
            }
        }
    }
}