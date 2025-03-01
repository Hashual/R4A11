package fr.unilim.iut.shi_fou_mi.logic.weapons

import fr.unilim.iut.shi_fou_mi.logic.Weapon

object WeaponRepository {
    val weapons: List<Weapon> = listOf(
        Rock(),
        Cisors(),
        Paper()
    )
}