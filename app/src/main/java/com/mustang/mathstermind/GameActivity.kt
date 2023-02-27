package com.mustang.mathstermind

import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.util.*
import kotlin.properties.Delegates
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    lateinit var textScore : TextView
    lateinit var textLife : TextView
    lateinit var textTime : TextView
    lateinit var linearLayoutGame : LinearLayout

    lateinit var textQuestion : TextView
    lateinit var editTextAnswer : EditText

    lateinit var buttonOK : Button
    lateinit var buttonNext : Button

    var correctAnswer = 0
    var userScore = 0
    var userLife = 3

    lateinit var timer : CountDownTimer
    private val startTimerInMillis : Long = 30000
    var timeLeftInMillis : Long = startTimerInMillis

    lateinit var actionTitle : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        actionTitle = intent.getStringExtra("actionTitle").toString()

        // Action Title update
        when (actionTitle) {
            "Addition" -> supportActionBar!!.title = "Addition"
            "Subtraction" -> supportActionBar!!.title = "Subtraction"
            "Multiplication" -> supportActionBar!!.title = "Multiplication"
        }

        textScore = findViewById(R.id.textViewScore)
        textLife = findViewById(R.id.textViewLife)
        textTime = findViewById(R.id.textViewTime)
        textQuestion = findViewById(R.id.textViewQuestion)
        editTextAnswer = findViewById(R.id.editTextAnswer)
        buttonOK = findViewById(R.id.buttonOk)
        buttonNext = findViewById(R.id.buttonNext)
        linearLayoutGame = findViewById(R.id.linearLayoutGame)

        // Game on
        gameContinue()

        //Ok Button
        buttonOK.setOnClickListener {

            val input = editTextAnswer.text.toString()

            // When user presses OK without entering the answer
            if(input == "") {
                Toast.makeText(applicationContext,"Please write an answer",
                Toast.LENGTH_LONG).show()
            } // When user enters the answer
            else {
                pauseTimer()
                buttonOK.isClickable = false

                val userAnswer = input.toInt()

                // When the answer is right
                if(userAnswer == correctAnswer) {
                    userScore += 10
                    textQuestion.text = "Congratulations, your answer is correct!"
                    textScore.text = userScore.toString()
                } // When the answer is wrong
                else {
                    userLife--
                    textQuestion.text = "Sorry, your answer is wrong"
                    textLife.text = userLife.toString()
                }
            }
        }

        //Next Button
        buttonNext.setOnClickListener {

            buttonOK.isClickable = true

            pauseTimer()
            resetTimer()

            editTextAnswer.setText("")

            if (userLife == 0) {
                Toast.makeText(applicationContext,"Game Over",Toast.LENGTH_LONG).show()
                val intent = Intent(this@GameActivity,ResultActivity::class.java)
                intent.putExtra("score",userScore)
                startActivity(intent)
                finish()
            } else {
                gameContinue()
            }

        }

    }
    //Game Logic
    private fun gameContinue() {

        val number1 = Random.nextInt(0,100)
        val number2 = Random.nextInt(0,100)

        // When addition button is clicked
        if(actionTitle == "Addition")
        {
            textQuestion.text = "$number1 + $number2"

            correctAnswer = number1 + number2

        }
        // When subtraction button is clicked
        else if (actionTitle == "Subtraction")
        {
            if (number1 >= number2)
            {
                textQuestion.text = "$number1 - $number2"

                correctAnswer = number1 - number2
            }
            else
            {
                textQuestion.text = "$number2 - $number1"

                correctAnswer = number2 - number1
            }
            linearLayoutGame.setBackgroundResource(R.drawable.math3)
        }
        // When multiplication button is clicked
        else
        {
            val number3 = Random.nextInt(0,20)
            val number4 = Random.nextInt(0,20)

            textQuestion.text = "$number3 * $number4"

            correctAnswer = number3 * number4

            linearLayoutGame.setBackgroundResource(R.drawable.math4)
        }

        // Calling the timer function
        startTimer()

    }

    // Timer Logic
    private fun startTimer() {

        timer = object : CountDownTimer(timeLeftInMillis,1000) {

            // Update timer every second
            override fun onTick(millisUntilFinished: Long) {

                timeLeftInMillis = millisUntilFinished
                updateText()

            }

            // After 60 seconds
            override fun onFinish() {

                pauseTimer()
                resetTimer()
                updateText()

                userLife--
                textLife.text = userLife.toString()
                textQuestion.text = "Sorry, time is up!"
                buttonOK.isClickable = false

            }

        }.start()

    }

    // Updating the timer textView
    fun updateText() {
        val remainingTime : Int = (timeLeftInMillis / 1000).toInt()
        textTime.text = String.format(Locale.getDefault(),"%02d",remainingTime)
    }

    fun pauseTimer() {
        timer.cancel()
    }

    fun resetTimer() {
        timeLeftInMillis = startTimerInMillis
        updateText()
    }

    // When user presses back button
    override fun onBackPressed() {

        // Dialog Box
        val alert = AlertDialog.Builder(this,R.style.MyDialogTheme)
        alert.setTitle("Go back")
        alert.setMessage("Are you sure?")
        alert.setCancelable(false)

        // When Yes is clicked
        alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->

            super.onBackPressed()
            finish()

        })
        // When NO is clicked
        alert.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->

            dialogInterface.cancel()

        })

        alert.create().show()

    }

}