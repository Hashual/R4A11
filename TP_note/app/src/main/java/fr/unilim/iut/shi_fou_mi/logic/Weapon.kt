package fr.unilim.iut.shi_fou_mi.logic

import fr.unilim.iut.shi_fou_mi.logic.weapons.Cisors
import fr.unilim.iut.shi_fou_mi.logic.weapons.Paper
import fr.unilim.iut.shi_fou_mi.logic.weapons.Rock
import kotlin.reflect.KClass

abstract class Weapon {
    abstract fun getDrawableResource(isLeft: Boolean): Int

    fun fightAgainst(other: Weapon): Int {
        return RULES[this::class]?.get(other::class) ?: 0
    }

    companion object {
        private val RULES: Map<KClass<out Weapon>, Map<KClass<out Weapon>, Int>> = mapOf(
            Rock::class to mapOf(Rock::class to 0, Paper::class to -1, Cisors::class to 1),
            Paper::class to mapOf(Rock::class to 1, Paper::class to 0, Cisors::class to -1),
            Cisors::class to mapOf(Rock::class to -1, Paper::class to 1, Cisors::class to 0)
        )
    }
}