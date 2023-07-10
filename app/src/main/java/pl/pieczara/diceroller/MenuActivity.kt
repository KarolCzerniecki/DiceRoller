package pl.pieczara.diceroller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class MenuActivity : Activity() {
    private lateinit var settingsButton: Button
    private lateinit var scoreboardButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        settingsButton = findViewById(R.id.settings_button)
        scoreboardButton = findViewById(R.id.scoreboard_button)

        settingsButton.setOnClickListener {
            openSettings()
        }

        scoreboardButton.setOnClickListener {
            openScoreboard()
        }

    }

    private fun openSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun openScoreboard() {
        val intent = Intent(this, ScoreboardActivity::class.java)
        startActivity(intent)
    }

}
