package fr.unilim.iut.shi_fou_mi.utils

import fr.unilim.iut.shi_fou_mi.R

enum class Languages(private val drawableRes: Int) {
    FR(R.drawable.flag_fr),
    EN(R.drawable.flag_en),
    DE(R.drawable.flag_de),
    ES(R.drawable.flag_es);

    fun getDrawableResource(): Int {
        return drawableRes
    }

    companion object {
        fun all(): List<Languages> {
            return listOf(FR, EN, DE, ES)
        }
    }
}