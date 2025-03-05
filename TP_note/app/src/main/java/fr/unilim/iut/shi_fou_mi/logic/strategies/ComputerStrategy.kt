package fr.unilim.iut.shi_fou_mi.logic.strategies

import fr.unilim.iut.shi_fou_mi.logic.Strategy
import fr.unilim.iut.shi_fou_mi.logic.Weapon
import fr.unilim.iut.shi_fou_mi.logic.weapons.WeaponRepository

class ComputerStrategy : Strategy() {
    override fun getMove(opponentHistory: List<Weapon>): Weapon {
        val mostCommonWeapon = opponentHistory.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key

        if (mostCommonWeapon != null) {
            return mostCommonWeapon.getCounterMove()
        }

        return WeaponRepository.weapons.random()
    }
}