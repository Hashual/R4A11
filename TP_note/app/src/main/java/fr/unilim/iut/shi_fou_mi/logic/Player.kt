package fr.unilim.iut.shi_fou_mi.logic

data class Player(val name: String, var score: Int = 0) {
    fun incrementScore() {
        score++
    }
}