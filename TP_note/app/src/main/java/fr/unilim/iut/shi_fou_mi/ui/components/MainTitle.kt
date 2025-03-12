package fr.unilim.iut.shi_fou_mi.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun MainTitle() {
    CustomText(
        text = "Shake-fou-mi",
        fontSize = 50.sp,
        textAlign = TextAlign.Center
    )
}