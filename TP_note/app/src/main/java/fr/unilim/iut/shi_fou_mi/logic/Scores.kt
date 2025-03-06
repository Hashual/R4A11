package fr.unilim.iut.shi_fou_mi.logic

import android.content.Context

class Scores(context: Context) {
    private val prefs = context.getSharedPreferences("scores", Context.MODE_PRIVATE)
    private val scores = mutableMapOf<String, Int>()

    init {
        loadScores()
    }

    fun updateScore(name: String) {
        scores[name] = (scores[name] ?: 0) + 1
        saveScores()
    }

    fun getScore(name: String): Int? = scores[name]

    fun getAllScores(): Map<String, Int> = scores

    fun getTop10Players(): List<Pair<String, Int>> {
        return scores.entries
            .sortedByDescending { it.value }
            .take(10)
            .map { it.key to it.value }
    }

    private fun saveScores() {
        val editor = prefs.edit()
        editor.putString("scores_map", scores.map { "${it.key}:${it.value}" }.joinToString(","))
        editor.apply()
    }

    private fun loadScores() {
        val savedData = prefs.getString("scores_map", "") ?: ""
        if (savedData.isNotEmpty()) {
            savedData.split(",").forEach {
                val parts = it.split(":")
                if (parts.size == 2) {
                    val name = parts[0]
                    val score = parts[1].toIntOrNull()
                    if (score != null) {
                        scores[name] = score
                    }
                }
            }
        }
    }

}

