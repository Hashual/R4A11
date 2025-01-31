package com.example.tp2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp2.ui.theme.TP2Theme


const val EXTRA_TEXT = "Username"
const val BIRTH = "Birth"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TP2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {

                            Greeting(
                                modifier = Modifier
                                    .padding(innerPadding)

                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            var text by remember { mutableStateOf("") }
                            var birth by remember { mutableIntStateOf(0) }


                            titleText(
                                text = text,
                                modifier = Modifier.padding(innerPadding),
                            )
                            Spacer(modifier = Modifier.height(0.dp))
                            TextField(
                                value = text,
                                onValueChange = { text=it },
                                label = { Text("Saisir votre nom") },
                                modifier = Modifier.padding(innerPadding)
                            )
                            Spacer(modifier = Modifier.height(0.dp))
                            TextField(
                                value = birth.toString(),
                                onValueChange = { birth=it.toIntOrNull()?:0 },
                                label = { Text("Saisir votre année de naissance") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.padding(innerPadding)

                            )
                            Spacer(modifier = Modifier.height(0.dp))
                            Button(
                                onClick = {

                                    if (text.isNotEmpty()) {
                                        val intent = Intent(this@MainActivity, MainActivity2::class.java)
                                        intent.putExtra(EXTRA_TEXT, text.toString())
                                        intent.putExtra(BIRTH, birth)
                                        startActivity(intent)                                    }
                                    else {
                                        Toast.makeText(this@MainActivity,"Texte vide", Toast.LENGTH_SHORT).show()
                                    }


                                },
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                Text("Valider")
                            }

                        }
                    }
                }
            }
        }
    }


@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Text(
        text = "Bienvenue",
        modifier = modifier,
        fontWeight = FontWeight(1000)
    )
}

@Composable
fun  titleText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = "$text",
        modifier = modifier,
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TP2Theme {
    }
}