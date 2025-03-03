package fr.unilim.iut.shi_fou_mi.ui.screens

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.unilim.iut.shi_fou_mi.R
import fr.unilim.iut.shi_fou_mi.ui.components.CustomButton
import fr.unilim.iut.shi_fou_mi.ui.components.MainTitle
import fr.unilim.iut.shi_fou_mi.ui.components.Screen

val titleFont = FontFamily(
    Font(R.font.musashi, FontWeight.Normal, FontStyle.Normal),
)

@Composable
fun HomeScreen(navController: NavController) {
    Screen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            MainTitle()
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (LocalConfiguration.current.screenHeightDp * 0.6).dp)
        ) {
            CustomButton(
                onClick = { navController.navigate("choosename") },
                text = "JOUER",
                padV = 16,
                padH = 32,
                textSize = 24
            )
        }
    }
}

//TODO() : faire choisir son pseudo au joueur max 7 lettres et faire aussi le read me analyse strat




