package fr.unilim.iut.shi_fou_mi.logic.strategies

import fr.unilim.iut.shi_fou_mi.logic.Strategy
import fr.unilim.iut.shi_fou_mi.logic.Weapon
import fr.unilim.iut.shi_fou_mi.logic.weapons.WeaponRepository

class AdaptiveStrategy : Strategy() {
    override fun getMove(opponentHistory: List<Weapon>): Weapon {
        if (opponentHistory.size >= 2 && opponentHistory.takeLast(2).map { it::class }.distinct().size == 1) {
            return opponentHistory.last().getCounterMove()
        }

        return WeaponRepository.weapons.random()
    }
}