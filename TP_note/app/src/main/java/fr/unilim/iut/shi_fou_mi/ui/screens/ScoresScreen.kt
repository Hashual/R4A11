package fr.unilim.iut.shi_fou_mi.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.unilim.iut.shi_fou_mi.logic.Scores
import fr.unilim.iut.shi_fou_mi.ui.components.CustomButton
import fr.unilim.iut.shi_fou_mi.ui.components.CustomText
import fr.unilim.iut.shi_fou_mi.ui.components.ResetScoresDialog
import fr.unilim.iut.shi_fou_mi.ui.components.Scorebaord
import fr.unilim.iut.shi_fou_mi.ui.components.Screen
import fr.unilim.iut.shi_fou_mi.utils.LanguageManager
import fr.unilim.iut.shi_fou_mi.utils.capitalizeFirstLetter

@Composable
fun ScoresScreen(navController: NavController) {
    val scores = Scores(LocalContext.current)
    var showDialog by remember { mutableStateOf(false) }
    val top10Players = scores.getTop10Players()
    Screen {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(70.dp))
            CustomText(LanguageManager.getLexicon().scores.capitalizeFirstLetter(), 50.sp, TextAlign.Center)
            Spacer(modifier = Modifier.height(15.dp))
            Scorebaord(top10Players, onClick = { showDialog = true })
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (LocalConfiguration.current.screenHeightDp * 0.6).dp)
        ) {
            CustomButton(
                onClick = { navController.navigate("home") },
                text = LanguageManager.getLexicon().back.uppercase(),
                padV = 16,
                padH = 32,
                textSize = 24
            )
        }
    }
    ResetScoresDialog(
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        onConfirm = {
            showDialog = false
            scores.resetScores()
        }
    )
}