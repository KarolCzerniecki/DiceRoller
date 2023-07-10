package pl.pieczara.diceroller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.Random

class MainActivity : Activity() {
    private lateinit var rollButton: Button
    private lateinit var menuButton: Button
    private lateinit var resultText: TextView
    private val random = Random()
    private lateinit var dbHelper: DBHelper
    private var canRoll = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rollButton = findViewById(R.id.roll_button)
        menuButton = findViewById(R.id.menu_button)
        resultText = findViewById(R.id.result_text)

        rollButton.setOnClickListener {
            if (canRoll) {
                canRoll = false
                rollDice()
                Handler().postDelayed({ canRoll = true }, 3000) // Odblokuj możliwość kolejnego losowania po 3 sekundach
            }
            else {
                Toast.makeText(this, "Nie tak szybko :)", Toast.LENGTH_SHORT).show()
            }
        }

        menuButton.setOnClickListener {
            openMenu()
        }

        dbHelper = DBHelper(this)
    }

    private fun rollDice() {
        val numDice = SettingsActivity.getNumDice(this)
        val numSides = SettingsActivity.getNumSides(this)

        val results = ArrayList<Int>()
        for (i in 1..numDice) {
            val randomNumber = random.nextInt(numSides) + 1
            results.add(randomNumber)
        }

        resultText.text = results.joinToString(", ")
        showToast("Rolled: ${results.joinToString(", ")}")

        // Zapisz wyniki do bazy danych
        dbHelper.addScore(results)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun openMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
}
