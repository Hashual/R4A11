package fr.unilim.iut.shi_fou_mi.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.unilim.iut.shi_fou_mi.logic.Scores
import fr.unilim.iut.shi_fou_mi.ui.components.CustomButton
import fr.unilim.iut.shi_fou_mi.ui.components.MainTitle
import fr.unilim.iut.shi_fou_mi.ui.components.Screen
import fr.unilim.iut.shi_fou_mi.utils.LanguageManager

@Composable
fun ScoresScreen(navController: NavController) {
    val scores = Scores(LocalContext.current)
    val top10Players = scores.getTop10Players()
    Screen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            MainTitle()
            Spacer(modifier = Modifier.height(32.dp))

            // Affichage des 10 meilleurs joueurs
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(top10Players.size) { index ->
                    val player = top10Players[index]
                    PlayerItem(player.first, player.second)
                }
            }
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
}

@Composable
fun PlayerItem(name: String, score: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = name)
            Text(text = score.toString())
        }
    }
}