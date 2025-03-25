package fr.unilim.iut.shi_fou_mi.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import fr.unilim.iut.shi_fou_mi.logic.FireworkParticle
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun Fireworks(onAnimationEnd: () -> Unit) {
    val particles = remember { mutableStateListOf<FireworkParticle>() }

    LaunchedEffect(Unit) {
        val centerX = 600f
        val centerY = 800f

        repeat(300) {
            particles.add(FireworkParticle(
                x = centerX,
                y = centerY,
                velocityX = Random.nextFloat() * 12f - 6f,
                velocityY = Random.nextFloat() * -15f - 5f,
                size = Random.nextFloat() * 6f + 3f,
                color = Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat(),
                    1f
                )
            ))
        }

        delay(2500)
        onAnimationEnd()
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        for (particle in particles) {
            drawCircle(
                color = particle.color,
                radius = particle.size,
                center = Offset(particle.x, particle.y)
            )
        }
    }

    LaunchedEffect(Unit) {
        while (particles.isNotEmpty()) {
            particles.replaceAll { particle ->
                particle.copy(
                    x = particle.x + particle.velocityX,
                    y = particle.y + particle.velocityY,
                    velocityY = particle.velocityY + 0.3f
                )
            }
            particles.removeAll { it.y > 2000f }
            delay(16L)
        }
    }
}

