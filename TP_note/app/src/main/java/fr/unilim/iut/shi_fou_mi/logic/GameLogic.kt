package fr.unilim.iut.shi_fou_mi.logic

import androidx.compose.runtime.MutableIntState
import fr.unilim.iut.shi_fou_mi.R

abstract class GameLogic() {
    protected val rightSymbols = listOf(R.drawable.rock_right, R.drawable.paper_right, R.drawable.cisors_right)
    protected val leftSymbols = listOf(R.drawable.rock_left, R.drawable.paper_left, R.drawable.cisors_left)

    fun launchRound(leftHandImage: MutableIntState, rightHandImage: MutableIntState) {
        applyGameLogic(leftHandImage, rightHandImage)
    }

    protected abstract fun applyGameLogic(leftHandImage: MutableIntState, rightHandImage: MutableIntState)
}