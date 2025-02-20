package fr.unilim.iut.shi_fou_mi.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(
    onClick : () -> Unit,
    text: String,
    padV: Int = 0,
    padH: Int = 0,
    textSize: Int = 16,
    width: Int? = null
) {
    Button(
        onClick = { onClick() },
        border = BorderStroke(1.dp, Color.Black),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8B86A)),
        contentPadding = PaddingValues(horizontal = padH.dp, vertical = padV.dp),
        modifier = Modifier.then(
            if (width != null) Modifier.width(width.dp) else Modifier
        )
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = textSize.sp,
        )
    }
}
