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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import fr.unilim.iut.shi_fou_mi.ui.screens.titleFont

@Composable
fun CustomText(
    text: String,
    fontSize: TextUnit,
    textAlign: TextAlign,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        contentAlignment = when (textAlign) {
            TextAlign.Start -> Alignment.CenterStart
            TextAlign.End -> Alignment.CenterEnd
            else -> Alignment.Center
        }
    ) {
        Text(
            text = text,
            fontFamily = titleFont,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = fontSize,
                shadow = Shadow(
                    color = Color(0xFF3D3648), // Marron bois
                    offset = Offset(10f, 10f),
                    blurRadius = 3f
                )
            ),
            textAlign = textAlign,
            color = Color.Transparent
        )

        Text(
            text = text,
            fontFamily = titleFont,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = fontSize,
                shadow = Shadow(
                    color = Color(0xFF52292B), // Marron bois
                    offset = Offset(10f, 10f),
                    blurRadius = 3f
                )
            ),
            textAlign = textAlign,
            color = Color(0xFFFFF59D) // Jaune pâle
        )
    }
}
