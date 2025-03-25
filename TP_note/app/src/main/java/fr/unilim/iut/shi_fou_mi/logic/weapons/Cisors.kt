package fr.unilim.iut.shi_fou_mi.logic.weapons

import fr.unilim.iut.shi_fou_mi.R
import fr.unilim.iut.shi_fou_mi.logic.Weapon

class Cisors : Weapon() {
    override fun getDrawableResource(isLeft: Boolean) = if (isLeft) R.drawable.cisors_left else R.drawable.cisors_right
    override fun copy() = Cisors()
}