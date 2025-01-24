package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

const val EXTRA_TEXT = "text_to_display"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }



        val premierBouton : Button = findViewById(R.id.buttonclic)
        val next : Button = findViewById(R.id.next)

        val premierLayout : EditText = findViewById(R.id.editText)
        val premieraffichage : TextView = findViewById(R.id.affichage)
        val layoutPrincipal : ConstraintLayout = findViewById(R.id.main)
        val deuxiemeTextView : TextView = TextView(this)
        var validerClick : Boolean = false


        premierBouton.setOnClickListener {
            premieraffichage.setText(premierLayout.text)
            validerClick = true

            if (premierLayout.text.toString() == "afficher nouveau textView"){
                deuxiemeTextView.setText(premierLayout.text)
                layoutPrincipal.addView(deuxiemeTextView)
            }

        }

        next.setOnClickListener{

            if ( validerClick ){
                val intent = Intent(this@MainActivity, MainActivity2::class.java)
                intent.putExtra(EXTRA_TEXT, premieraffichage.text.toString())
                startActivity(intent)

            }
        }



    }
}