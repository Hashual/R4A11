package fr.unilim.iut.shi_fou_mi.logic

abstract class GameLogic {
    fun launchRound() : Pair<Weapon, Weapon> {
        return applyGameLogic()
    }

    protected abstract fun applyGameLogic() : Pair<Weapon, Weapon>
}