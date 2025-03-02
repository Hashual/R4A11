package fr.unilim.iut.shi_fou_mi.logic.strategies

import fr.unilim.iut.shi_fou_mi.logic.Strategy
import fr.unilim.iut.shi_fou_mi.logic.Weapon
import fr.unilim.iut.shi_fou_mi.logic.weapons.Cisors
import fr.unilim.iut.shi_fou_mi.logic.weapons.Paper
import fr.unilim.iut.shi_fou_mi.logic.weapons.Rock
import fr.unilim.iut.shi_fou_mi.logic.weapons.WeaponRepository

class HumanStrategy : Strategy() {
    override fun getMove(opponentHistory: List<Weapon>): Weapon {
        if (opponentHistory.isEmpty()) return Rock()

        if (opponentHistory.size >= 2 && opponentHistory.takeLast(2).all { it is Rock }) {
            return Paper()
        }

        val lastMove = opponentHistory.lastOrNull()
        return when (lastMove) {
            is Rock -> Paper()
            is Paper -> Cisors()
            is Cisors -> Rock()
            else -> WeaponRepository.weapons.random()
        }
    }
}