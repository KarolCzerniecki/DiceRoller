package pl.pieczara.diceroller

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView

class SettingsActivity : Activity() {
    private lateinit var numDiceSeekBar: SeekBar
    private lateinit var numDiceText: TextView
    private lateinit var numSidesSeekBar: SeekBar
    private lateinit var numSidesText: TextView

    companion object {
        private const val DEFAULT_NUM_DICE = 1
        private const val DEFAULT_NUM_SIDES = 6
        private const val MIN_NUM_SIDES = 6
        private const val MAX_NUM_SIDES = 24

        fun getNumDice(context: Context): Int {
            val sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
            return sharedPreferences.getInt("numDice", DEFAULT_NUM_DICE)
        }

        fun getNumSides(context: Context): Int {
            val sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
            return sharedPreferences.getInt("numSides", DEFAULT_NUM_SIDES)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        numDiceSeekBar = findViewById(R.id.num_dice_seekbar)
        numDiceText = findViewById(R.id.num_dice_text)
        numSidesSeekBar = findViewById(R.id.num_sides_seekbar)
        numSidesText = findViewById(R.id.num_sides_text)

        numDiceSeekBar.max = 3
        numDiceSeekBar.progress = getNumDice(this) - 1
        numDiceText.text = "Number of dice: ${getNumDice(this)}"

        numSidesSeekBar.max = MAX_NUM_SIDES - MIN_NUM_SIDES
        numSidesSeekBar.progress = getNumSides(this) - MIN_NUM_SIDES
        numSidesText.text = "Number of sides: ${getNumSides(this)}"

        numDiceSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val numDice = progress + 1
                numDiceText.text = "Number of dice: $numDice"
                saveNumDice(numDice)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        numSidesSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val numSides = progress + MIN_NUM_SIDES
                numSidesText.text = "Number of sides: $numSides"
                saveNumSides(numSides)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun saveNumDice(numDice: Int) {
        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt("numDice", numDice)
        editor.apply()
    }

    private fun saveNumSides(numSides: Int) {
        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt("numSides", numSides)
        editor.apply()
    }
}
