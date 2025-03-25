package fr.unilim.iut.shi_fou_mi.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.unilim.iut.shi_fou_mi.utils.LanguageManager
import fr.unilim.iut.shi_fou_mi.utils.capitalizeFirstLetter

@SuppressLint("DefaultLocale")
@Composable
fun Scorebaord(top10Players: List<Pair<String, Int>>, onClick: () -> Unit) {
    val playersWithDefaultScores = List(10) { index ->
        if (index < top10Players.size) {
            top10Players[index]
        } else {
            Pair("___", 0)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(0.8f)
            .shadow(8.dp, shape = RoundedCornerShape(12.dp))
            .background(Color(0xFFE8B86A), shape = RoundedCornerShape(12.dp))
            .clickable(onClick = { onClick() })
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = LanguageManager.getLexicon().player.capitalizeFirstLetter(), fontWeight = FontWeight.Bold, color = Color.White, fontSize = 22.sp)
                Text(text = LanguageManager.getLexicon().victories.capitalizeFirstLetter(), fontWeight = FontWeight.Bold, color = Color.White, fontSize = 22.sp)
            }
            HorizontalDivider(
                color = Color.White,
                thickness = 2.dp,
                modifier = Modifier
                    .height(4.dp)
                    .fillMaxWidth())
        }
        items(playersWithDefaultScores.size) { index ->
            val player = playersWithDefaultScores[index]

            val (textColor, fontSize) = when (index) {
                0 -> Pair(Color(0xFFFFD700), 22.sp)
                1 -> Pair(Color(0xFFC0C0C0), 20.sp)
                2 -> Pair(Color(0xFFCD7F32), 20.sp)
                else -> Pair(Color.White, 18.sp)
            }

            val position = when (index) {
                0 -> "\uD83C\uDFC6"
                1 -> "\uD83E\uDD48"
                2 -> "\uD83E\uDD49"
                else -> if (index + 1 < 10)  {"  ${index + 1}"} else index + 1
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${position}. ${player.first}",
                    color = textColor,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color(0xFF3D3648), // Marron bois
                            offset = Offset(3f, 3f),
                            blurRadius = 5f
                        )
                    ),
                )
                Text(
                    text = player.second.toString(),
                    color = textColor,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color(0xFF3D3648), // Marron bois
                            offset = Offset(3f, 3f),
                            blurRadius = 3f
                        )
                    ),
                )
            }
        }
    }
}