package fr.unilim.iut.shi_fou_mi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.unilim.iut.shi_fou_mi.ui.theme.Shi_fou_miTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Shi_fou_miTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("play") {
            PlayScreen(navController = navController)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Bienvenue dans le Shake-Fou-Mi",
            style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.navigate("play") }) {
            Text(text = "Jouer")
        }

    }
}

@Composable
fun PlayScreen(navController: NavController) {
    var rotation by remember { mutableFloatStateOf(0f) }

    val animatedRotation by animateFloatAsState(
        targetValue = rotation,
        animationSpec = keyframes {
            durationMillis = 1000
            0f at 0
            20f at 200
            0f at 400
            20f at 600
            0f at 800
            20f at 1000
            0f at 1200
        },
        label = "rotationAnimation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Image en arrière-plan
        Image(
            painter = painterResource(id = R.drawable.background_game),
            contentDescription = "background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Image(
            painter = painterResource(id = R.drawable.pierre_gauche),
            contentDescription = "hand left player",
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.BottomStart)
                .absoluteOffset(x = (-30).dp, y = (-230).dp)
        )

        Image(
            painter = painterResource(id = R.drawable.pierre_droit),
            contentDescription = "hand right player",
            modifier = Modifier
                .size(250.dp)
                .rotate(animatedRotation)
                .align(Alignment.BottomEnd)
                .absoluteOffset(x = (30).dp, y = (-230).dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Page de jeu",
                style = MaterialTheme.typography.titleMedium
            )
            Button(onClick = { navController.popBackStack() }) {
                Text(text = "Retour")
            }
            Button(onClick = {
                // Réinitialise la rotation à 0, ce qui déclenche l'animation
                rotation = 0.0001f
            }) {
                Text(text = "Animer")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Shi_fou_miTheme {
        AppNavigation()
    }
}