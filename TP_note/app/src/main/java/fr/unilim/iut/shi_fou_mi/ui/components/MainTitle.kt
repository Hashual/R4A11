package fr.unilim.iut.shi_fou_mi.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.unilim.iut.shi_fou_mi.ui.screens.titleFont

@Composable
fun MainTitle() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        contentAlignment = Alignment.Center
    )
    {

        Text(
            text = "Shake-fou-mi",
            fontFamily = titleFont,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 54.sp,
                shadow = Shadow(
                    color = Color(0xFF3D3648), // Marron bois
                    offset = Offset(10f, 10f),
                    blurRadius = 3f
                )
            ),
            textAlign = TextAlign.Center,
            color = Color.Transparent
        )

        Text(
            text = "Shake-fou-mi",
            fontFamily = titleFont,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 54.sp,
                shadow = Shadow(
                    color = Color(0xFF52292B), // Marron bois
                    offset = Offset(10f, 10f),
                    blurRadius = 3f
                )
            ),
            textAlign = TextAlign.Center,
            color = Color(0xFFFFF59D) // Jaune pâle
        )
    }
}