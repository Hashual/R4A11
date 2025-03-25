package fr.unilim.iut.shi_fou_mi.utils.lexicons

data object FrenchLexicon : Lexicon() {
    override val win = "gagne"
    override val draw = "match nul"
    override val play = "jouer"
    override val pseudo = "pseudo"
    override val choosePseudo = "Choisissez votre pseudo (max 7 caractères)"
    override val chooseOpponent = "Choisissez votre adversaire"
    override val computer = "ordinateur"
    override val player = "joueur"
    override val chooseMode = "Choisissez le mode"
    override val classic = "classique"
    override val strategic = "stratégique"
    override val chooseAdversaryStrategy = "Choisissez la stratégie adverse"
    override val chooseYourStrategy = "Choisissez votre stratégie"
    override val human = "humaine"
    override val machine = "machine"
    override val adaptive = "adaptative"
    override val back = "retour"
}