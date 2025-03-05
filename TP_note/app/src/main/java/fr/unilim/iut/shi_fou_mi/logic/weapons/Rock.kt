package fr.unilim.iut.shi_fou_mi.logic.weapons

import fr.unilim.iut.shi_fou_mi.R
import fr.unilim.iut.shi_fou_mi.logic.Weapon

class Rock : Weapon() {
    override fun getDrawableResource(isLeft: Boolean) = if (isLeft) R.drawable.rock_left else R.drawable.rock_right
    override fun copy() = Rock()
}