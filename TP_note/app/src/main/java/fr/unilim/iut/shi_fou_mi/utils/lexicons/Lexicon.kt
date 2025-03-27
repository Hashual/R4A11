package fr.unilim.iut.shi_fou_mi.utils.lexicons

sealed class Lexicon {
    abstract val win: String
    abstract val draw: String
    abstract val play: String
    abstract val pseudo: String
    abstract val choosePseudo: String
    abstract val chooseOpponent: String
    abstract val computer: String
    abstract val player: String
    abstract val chooseMode: String
    abstract val classic: String
    abstract val strategic: String
    abstract val chooseAdversaryStrategy: String
    abstract val chooseYourStrategy: String
    abstract val human: String
    abstract val machine: String
    abstract val adaptive: String
    abstract val back: String
    abstract val scores: String
    abstract val victories: String
    abstract val confirm: String
    abstract val cancel: String
    abstract val yes: String
    abstract val resetscores: String
    abstract val hostGame: String
    abstract val joinGame: String
    abstract val waitingScreen: String
}
