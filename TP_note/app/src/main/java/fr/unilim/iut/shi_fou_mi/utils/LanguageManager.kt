package fr.unilim.iut.shi_fou_mi.utils

import fr.unilim.iut.shi_fou_mi.utils.lexicons.EnglishLexicon
import fr.unilim.iut.shi_fou_mi.utils.lexicons.FrenchLexicon
import fr.unilim.iut.shi_fou_mi.utils.lexicons.GermanLexicon
import fr.unilim.iut.shi_fou_mi.utils.lexicons.Lexicon
import fr.unilim.iut.shi_fou_mi.utils.lexicons.SpanishLexicon

object LanguageManager {
    private var currentLanguage: Languages = Languages.FR

    fun getLexicon(): Lexicon {
        return when (currentLanguage) {
            Languages.FR -> FrenchLexicon
            Languages.EN -> EnglishLexicon
            Languages.DE -> GermanLexicon
            Languages.ES -> SpanishLexicon
        }
    }

    fun setLanguage(lang: Languages) {
        currentLanguage = lang
    }

    fun getLanguage(): Languages {
        return currentLanguage
    }
}