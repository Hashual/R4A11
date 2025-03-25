package fr.unilim.iut.shi_fou_mi.logic

data class FireworkParticle(
    var x: Float,
    var y: Float,
    var velocityX: Float,
    var velocityY: Float,
    var size: Float,
    val color: androidx.compose.ui.graphics.Color
)