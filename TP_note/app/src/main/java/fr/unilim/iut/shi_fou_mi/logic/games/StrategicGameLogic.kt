package fr.unilim.iut.shi_fou_mi.logic.games

import fr.unilim.iut.shi_fou_mi.logic.GameLogic
import fr.unilim.iut.shi_fou_mi.logic.Strategy
import fr.unilim.iut.shi_fou_mi.logic.Weapon

class StrategicGameLogic : GameLogic() {
    override fun applyGameLogic(
        player1Strategy: Strategy?,
        player2Strategy: Strategy?,
        player1History: List<Weapon>,
        player2History: List<Weapon>
    ): Pair<Weapon, Weapon> {
        if (player1Strategy == null || player2Strategy == null) {
            throw IllegalArgumentException("Les stratégies des deux joueurs doivent être fournies.")
        }

        val weapon1 = player1Strategy.getMove(player2History)
        val weapon2 = player2Strategy.getMove(player1History)

        return weapon1 to weapon2
    }
}