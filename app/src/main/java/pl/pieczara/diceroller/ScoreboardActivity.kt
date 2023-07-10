package pl.pieczara.diceroller

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ScoreboardActivity : Activity() {
    private lateinit var scoreboardText: TextView
    private lateinit var clearButton: Button
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        scoreboardText = findViewById(R.id.scoreboard_text)
        clearButton = findViewById(R.id.clear_button)
        dbHelper = DBHelper(this)

        showScoreboard()

        clearButton.setOnClickListener {
            clearScoreboard()
        }
    }

    private fun showScoreboard() {
        val scores = dbHelper.getAllScores()
        val scoreboard = StringBuilder()
        for (score in scores) {
            scoreboard.append(score.toString())
            scoreboard.append("\n")
        }
        scoreboardText.text = scoreboard.toString()
    }

    private fun clearScoreboard() {
        dbHelper.clearScores()
        showScoreboard()
    }
}
