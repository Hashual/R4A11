package fr.unilim.iut.shi_fou_mi.logic.weapons

import fr.unilim.iut.shi_fou_mi.R
import fr.unilim.iut.shi_fou_mi.logic.Weapon

class Paper : Weapon() {
    override fun getDrawableResource(isLeft: Boolean) = if (isLeft) R.drawable.paper_left else R.drawable.paper_right
    override fun copy() = Paper()
}