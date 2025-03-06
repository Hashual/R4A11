package fr.unilim.iut.shi_fou_mi.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
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
import fr.unilim.iut.shi_fou_mi.utils.LanguageManager
import fr.unilim.iut.shi_fou_mi.utils.Languages

val titleFont = FontFamily(
    Font(R.font.musashi, FontWeight.Normal, FontStyle.Normal),
)

@Composable
fun HomeScreen(navController: NavController) {
    val currentLanguage = remember  { mutableStateOf(LanguageManager.getLanguage()) }

    fun changeLanguage() {
        val languages = Languages.all()
        val currentIndex = languages.indexOf(currentLanguage.value)
        val nextLanguage = if (currentIndex < languages.size - 1) {
            languages[currentIndex + 1]
        } else {
            languages[0]
        }

        currentLanguage.value = nextLanguage
        LanguageManager.setLanguage(nextLanguage)
    }

    Screen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            MainTitle()
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clickable(onClick = { changeLanguage() })
            ){
                Image(
                    painter = painterResource(id = currentLanguage.value.getDrawableResource()),
                    contentDescription = "Change language",
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (LocalConfiguration.current.screenHeightDp * 0.485).dp)
        ) {
            CustomButton(
                onClick = { navController.navigate("choosename") },
                text = LanguageManager.getLexicon().play.uppercase(),
                padV = 16,
                textSize = 24,
                width = 200
            )
            Spacer(modifier = Modifier.height(30.dp))
            CustomButton(
                onClick = { navController.navigate("scores") },
                text = LanguageManager.getLexicon().scores.uppercase(),
                padV = 16,
                textSize = 24,
                width = 200
            )
        }
    }
}




