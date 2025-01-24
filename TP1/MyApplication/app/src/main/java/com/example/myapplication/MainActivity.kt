package com.example.myapplication

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
        val premierLayout : EditText = findViewById(R.id.editText)
        val premieraffichage : TextView = findViewById(R.id.affichage)
        val layoutPrincipal : ConstraintLayout = findViewById(R.id.main)
        val deuxiemeTextView : TextView = TextView(this)


        premierBouton.setOnClickListener {
            premieraffichage.setText(premierLayout.text)

            if (premierLayout.text.toString() == "afficher nouveau textView"){
                deuxiemeTextView.setText(premierLayout.text)
                layoutPrincipal.addView(deuxiemeTextView)
            }

        }

    }
}