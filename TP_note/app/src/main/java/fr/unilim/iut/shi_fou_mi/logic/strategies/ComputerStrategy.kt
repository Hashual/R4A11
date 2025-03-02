package fr.unilim.iut.shi_fou_mi.logic.strategies

import fr.unilim.iut.shi_fou_mi.logic.Strategy
import fr.unilim.iut.shi_fou_mi.logic.Weapon
import fr.unilim.iut.shi_fou_mi.logic.weapons.Cisors
import fr.unilim.iut.shi_fou_mi.logic.weapons.Paper
import fr.unilim.iut.shi_fou_mi.logic.weapons.Rock
import fr.unilim.iut.shi_fou_mi.logic.weapons.WeaponRepository
import kotlin.reflect.KClass

class ComputerStrategy : Strategy() {
    override fun getMove(opponentHistory: List<Weapon>): Weapon {
        if (opponentHistory.isEmpty()) return WeaponRepository.weapons.random()

        val commonMove = opponentHistory.groupingBy { it::class }.eachCount().maxByOrNull { it.value }?.key
        return WeaponRepository.weapons.find { it::class == getCounterMove(commonMove) } ?: WeaponRepository.weapons.random()
    }

    private fun getCounterMove(moveClass: KClass<out Weapon>?): KClass<out Weapon>? {
        return when (moveClass) {
            Rock::class -> Paper::class
            Paper::class -> Cisors::class
            Cisors::class -> Rock::class
            else -> null
        }
    }
}