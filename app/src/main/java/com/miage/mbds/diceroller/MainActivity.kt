package com.miage.mbds.diceroller

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.exemple.diceroller.R


class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.button)
        val resultTextView1: TextView = findViewById(R.id.textView1)
        val resultTextView2: TextView = findViewById(R.id.textView2)
        val messageTextView: TextView = findViewById(R.id.resultTextView)
        val targetNumberEditText: EditText = findViewById(R.id.targetNumberInput)

        targetNumberEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                rollButton.isEnabled = !s.isNullOrEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        rollButton.setOnClickListener {
            val targetNumber = targetNumberEditText.text.toString().toIntOrNull()
            if (targetNumber == null || targetNumber < 2 || targetNumber > 12) {
                messageTextView.text = "Veuillez entrer un nombre entre 2 et 12."
                return@setOnClickListener
            }

            val result1 = rollDice()
            val result2 = rollDice()
            val sum = result1 + result2

            resultTextView1.text = "Dé 1 : $result1"
            resultTextView2.text = "Dé 2 : $result2"

            if (sum == targetNumber) {
                messageTextView.text = "Félicitations ! Vous avez gagné ! La somme est $sum."
            } else {
                messageTextView.text = "Dommage ! Vous avez perdu. La somme est $sum."
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

private fun rollDice(): Int {
    val dice = Dice(6)
    val diceRoll = dice.roll()
    return diceRoll
}


class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}