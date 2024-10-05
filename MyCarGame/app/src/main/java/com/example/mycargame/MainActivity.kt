package com.example.mycargame

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(),GameArea{
    private lateinit var rootLyout :LinearLayout
    private lateinit var startbutton :Button
    private lateinit var view: Gameview
    private lateinit var score:TextView
    private lateinit var highScoreText: TextView
    private var highScore: Int = 0
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("my_pref", Context.MODE_PRIVATE)
        highScore = sharedPreferences.getInt("high_score", 0)

        startbutton = findViewById(R.id.mystartBtn)
        rootLyout = findViewById(R.id.rootLyout)
        score = findViewById(R.id.myscore)
        highScoreText = findViewById(R.id.myhighscore)
        view = Gameview(this,this)

        startbutton.setOnClickListener {
            startGame()
        }
        updateHighScoreText()
    }

    private fun startGame() {
        view = Gameview(this, this)
        view.setBackgroundResource(R.drawable.jungle)
        rootLyout.addView(view)
        startbutton.visibility = View.GONE
        score.visibility = View.GONE


        val previousScore = sharedPreferences.getInt("current_score", 0)
        score.text = "Score: $previousScore"


        updateHighScoreText()
    }
    private fun updateHighScoreText() {
        highScoreText.text = "High Score: $highScore"
    }


    override fun closeGame(gameScore: Int) {

        if (gameScore > highScore) {
            highScore = gameScore
            sharedPreferences.edit().putInt("high_score", highScore).apply()
            updateHighScoreText()
        }

        sharedPreferences.edit().putInt("current_score", gameScore).apply()
        score.text = "MY SCORE : $gameScore"
        rootLyout.removeView(view)
        startbutton.visibility = View.VISIBLE
        score.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        startGame()
    }
}