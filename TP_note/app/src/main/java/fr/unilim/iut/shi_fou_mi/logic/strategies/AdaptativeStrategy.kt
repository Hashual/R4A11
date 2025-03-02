package fr.unilim.iut.shi_fou_mi.logic.strategies

import fr.unilim.iut.shi_fou_mi.logic.Strategy
import fr.unilim.iut.shi_fou_mi.logic.Weapon
import fr.unilim.iut.shi_fou_mi.logic.weapons.Cisors
import fr.unilim.iut.shi_fou_mi.logic.weapons.Paper
import fr.unilim.iut.shi_fou_mi.logic.weapons.Rock
import fr.unilim.iut.shi_fou_mi.logic.weapons.WeaponRepository

class AdaptiveStrategy : Strategy() {
    override fun getMove(opponentHistory: List<Weapon>): Weapon {
        if (opponentHistory.isEmpty()) return WeaponRepository.weapons.random()

        if (opponentHistory.size >= 2) {
            val lastTwoMoves = opponentHistory.takeLast(2)
            if (lastTwoMoves[0]::class == lastTwoMoves[1]::class) {
                return when (lastTwoMoves[1]) {
                    is Rock -> Paper()
                    is Paper -> Cisors()
                    is Cisors -> Rock()
                    else -> WeaponRepository.weapons.random()
                }
            }
        }

        return WeaponRepository.weapons.random()
    }
}