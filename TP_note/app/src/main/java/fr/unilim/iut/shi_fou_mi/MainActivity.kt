package fr.unilim.iut.shi_fou_mi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.unilim.iut.shi_fou_mi.ui.theme.Shi_fou_miTheme
import kotlinx.coroutines.launch
import kotlin.random.Random

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
        Text(
            text = "Bienvenue dans le Shake-Fou-Mi",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.navigate("play") }) {
            Text(text = "Jouer")
        }
    }
}

@Composable
fun PlayScreen(navController: NavController) {
    // Contexte pour instancier le gyroscope.
    val context = LocalContext.current

    // Animation de rotation pour l'animation des images.
    val rotation = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    // Listes d'images pour les symboles.
    val rightSymbols = listOf(R.drawable.rock_right, R.drawable.paper_right, R.drawable.cisors_right)
    val leftSymbols = listOf(R.drawable.rock_left, R.drawable.paper_left, R.drawable.cisors_left)
    val rightHandImage = remember { mutableIntStateOf(rightSymbols[0]) }
    val leftHandImage = remember { mutableIntStateOf(leftSymbols[0]) }

    fun changeLeftHandImage(newImage: Int) {
        leftHandImage.value = newImage
    }

    fun changeRightHandImage(newImage: Int) {
        rightHandImage.value = newImage
    }

    fun getRandomIndex(): Int = Random.nextInt(0, 3)

    // Fonction qui lance l'animation et change les images aléatoirement.
    fun launchRound() {
        coroutineScope.launch {
            repeat(3) {
                rotation.animateTo(0f, animationSpec = tween(100))
                rotation.animateTo(10f, animationSpec = tween(100))
            }
            rotation.animateTo(0f, animationSpec = tween(100))
            changeLeftHandImage(leftSymbols[getRandomIndex()])
            changeRightHandImage(rightSymbols[getRandomIndex()])
        }
    }

    // Création et démarrage du gyroscope qui déclenche launchRound() lors de 3 mouvements détectés.
    val gyroscope = remember {
        Gyroscope(context) {
            // Lorsque 3 mouvements sont détectés, lancer l'animation.
            launchRound()
        }
    }

    // Démarrage et arrêt automatique du gyroscope.
    androidx.compose.runtime.DisposableEffect(Unit) {
        gyroscope.start()
        onDispose { gyroscope.stop() }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Image d'arrière-plan.
        Image(
            painter = painterResource(id = R.drawable.background_game),
            contentDescription = "background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Image(
            painter = painterResource(id = leftHandImage.value),
            contentDescription = "hand left player",
            modifier = Modifier
                .size(250.dp)
                .graphicsLayer(rotationZ = -rotation.value)
                .align(Alignment.BottomStart)
                .absoluteOffset(x = (-40).dp, y = (-230).dp)
        )
        Image(
            painter = painterResource(id = rightHandImage.value),
            contentDescription = "hand right player",
            modifier = Modifier
                .size(250.dp)
                .graphicsLayer(rotationZ = rotation.value)
                .align(Alignment.BottomEnd)
                .absoluteOffset(x = (40).dp, y = (-230).dp)
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
            // Bouton "Animer" pour lancer manuellement l'animation.
            Button(onClick = { launchRound() }) {
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
