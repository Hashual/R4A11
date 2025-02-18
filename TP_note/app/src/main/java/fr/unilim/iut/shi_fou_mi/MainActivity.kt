package fr.unilim.iut.shi_fou_mi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import fr.unilim.iut.shi_fou_mi.ui.theme.Shi_fou_miTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    // Variable d'état pour afficher le message dans Compose.
    private var messageVisible = mutableStateOf(false)

    // Instance de la classe Gyroscope.
    private lateinit var gyroscope: Gyroscope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Création du gyroscope en passant un callback qui affiche le message.
        gyroscope = Gyroscope(this) {
            // Ce callback est exécuté sur le thread du gyroscope, on passe sur le UI thread.
            runOnUiThread {
                messageVisible.value = true
            }
            // Masquer le message après 3 secondes.
            lifecycleScope.launch {
                delay(3000)
                runOnUiThread {
                    messageVisible.value = false
                }
            }
        }
        gyroscope.start()

        enableEdgeToEdge()
        setContent {
            Shi_fou_miTheme {
                // UI principale affichant le message si nécessaire.
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        if (messageVisible.value) {
                            Surface(modifier = Modifier.align(Alignment.Center)) {
                                Text(text = "3 mouvements détectés !")
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        gyroscope.stop()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMain() {
    Shi_fou_miTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Surface(modifier = Modifier.align(Alignment.Center)) {
                Text(text = "3 mouvements détectés !")
            }
        }
    }
}
