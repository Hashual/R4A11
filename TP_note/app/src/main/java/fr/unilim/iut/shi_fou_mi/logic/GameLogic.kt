package fr.unilim.iut.shi_fou_mi.logic

abstract class GameLogic {
    fun launchRound(
        player1Strategy: Strategy? = null,
        player2Strategy: Strategy? = null,
        player1History: List<Weapon> = mutableListOf(),
        player2History: List<Weapon> = mutableListOf()
    ) : Pair<Weapon, Weapon> {
        return applyGameLogic(player1Strategy, player2Strategy, player1History, player2History)
    }

    protected abstract fun applyGameLogic(
        player1Strategy: Strategy?,
        player2Strategy: Strategy?,
        player1History: List<Weapon>,
        player2History: List<Weapon>
    ): Pair<Weapon, Weapon>
}