package fr.unilim.iut.shi_fou_mi.logic

abstract class Strategy {
    abstract fun getMove(opponentHistory: List<Weapon>): Weapon
}