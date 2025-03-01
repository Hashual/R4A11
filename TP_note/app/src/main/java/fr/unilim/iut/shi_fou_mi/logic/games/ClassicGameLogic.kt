package fr.unilim.iut.shi_fou_mi.logic.games

import androidx.compose.runtime.MutableIntState
import fr.unilim.iut.shi_fou_mi.logic.GameLogic
import fr.unilim.iut.shi_fou_mi.logic.Weapon
import fr.unilim.iut.shi_fou_mi.logic.weapons.WeaponRepository

class ClassicGameLogic() : GameLogic() {
    override fun applyGameLogic(): Pair<Weapon, Weapon> {
        return WeaponRepository.weapons.random() to WeaponRepository.weapons.random()
    }
}