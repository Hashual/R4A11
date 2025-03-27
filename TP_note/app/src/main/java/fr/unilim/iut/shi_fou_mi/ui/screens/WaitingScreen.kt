package fr.unilim.iut.shi_fou_mi.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import fr.unilim.iut.shi_fou_mi.ui.components.CustomButton
import fr.unilim.iut.shi_fou_mi.ui.components.CustomText
import fr.unilim.iut.shi_fou_mi.ui.components.Screen
import fr.unilim.iut.shi_fou_mi.utils.LanguageManager

@Composable
fun WaitingScreen(playerName: String) {
    Screen{
        Column {
            CustomText(LanguageManager.getLexicon().waitingScreen, 35.sp, TextAlign.Center)
        }
    }
}