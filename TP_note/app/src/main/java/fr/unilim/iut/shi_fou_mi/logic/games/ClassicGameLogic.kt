package fr.unilim.iut.shi_fou_mi.logic.games

import androidx.compose.runtime.MutableIntState
import fr.unilim.iut.shi_fou_mi.logic.GameLogic

class ClassicGameLogic() : GameLogic() {
    override fun applyGameLogic(leftHandImage: MutableIntState, rightHandImage: MutableIntState) {
        leftHandImage.intValue = leftSymbols.random()
        rightHandImage.intValue = rightSymbols.random()
    }
}