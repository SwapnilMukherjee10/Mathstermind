package com.mustang.mathstermind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    lateinit var addition : Button
    lateinit var subtraction : Button
    lateinit var multi : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        addition = findViewById(R.id.buttonAdd)
        subtraction = findViewById(R.id.buttonSub)
        multi = findViewById(R.id.buttonMulti)

        //Addition Button intent
        addition.setOnClickListener {

            val intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra("actionTitle","Addition")
            startActivity(intent)

        }

        // Subtraction button intent
        subtraction.setOnClickListener {

            val intent = Intent(this@MainActivity,GameActivity::class.java)
            intent.putExtra("actionTitle","Subtraction")
            startActivity(intent)

        }

        // Multiplication button intent
        multi.setOnClickListener {

            val intent = Intent(this@MainActivity,GameActivity::class.java)
            intent.putExtra("actionTitle","Multiplication")
            startActivity(intent)

        }

    }
}