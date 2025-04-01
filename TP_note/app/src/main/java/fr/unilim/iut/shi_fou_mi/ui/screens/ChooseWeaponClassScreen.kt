package fr.unilim.iut.shi_fou_mi.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.unilim.iut.shi_fou_mi.logic.Weapon
import fr.unilim.iut.shi_fou_mi.logic.weapons.WeaponRepository
import fr.unilim.iut.shi_fou_mi.ui.components.CustomButton
import fr.unilim.iut.shi_fou_mi.ui.components.CustomText
import fr.unilim.iut.shi_fou_mi.ui.components.Screen
import fr.unilim.iut.shi_fou_mi.utils.LanguageManager

@Composable
fun ChooseWeaponClassScreen(OnWeaponSelected: (Weapon) -> Unit) {
    var usedWeapon by remember { mutableStateOf<Weapon?>(null) }
    Screen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            for (weapon in WeaponRepository.weapons){
                Checkbox(
                    checked = usedWeapon == weapon,
                    onCheckedChange = { usedWeapon = weapon },
                )
                CustomText(
                    text = weapon::class.simpleName.toString(),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )

            }
            CustomButton(
                onClick = {
                    usedWeapon?.let { OnWeaponSelected(it) }
                },
                text = LanguageManager.getLexicon().joinGame,
                padV = 16,
                width = 200,
                textSize = 24
            )
        }


    }
}