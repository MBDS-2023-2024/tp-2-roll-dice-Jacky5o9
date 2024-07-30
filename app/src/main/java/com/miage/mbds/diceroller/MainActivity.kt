package com.miage.mbds.diceroller

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.exemple.diceroller.R


class MainActivity : AppCompatActivity() {

    private lateinit var diceImage1: ImageView
    private lateinit var diceImage2: ImageView
    private lateinit var targetNumberEditText: EditText
    private lateinit var resultTextView: TextView
    private val diceImages = listOf(
        R.drawable.dice_1, R.drawable.dice_2, R.drawable.dice_3,
        R.drawable.dice_4, R.drawable.dice_5, R.drawable.dice_6
    )

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        diceImage1 = findViewById(R.id.diceImage1)
        diceImage2 = findViewById(R.id.diceImage2)
        targetNumberEditText = findViewById(R.id.targetNumberInput)
        resultTextView = findViewById(R.id.resultTextView)

        val clickListener = { _: ImageView ->
            val targetNumber = targetNumberEditText.text.toString().toIntOrNull()
            if (targetNumber == null || targetNumber < 2 || targetNumber > 12) {
                resultTextView.text = "Veuillez entrer un nombre entre 2 et 12."
                return@setOnClickListener
            }

            val result1 = rollDice(diceImage1)
            val result2 = rollDice(diceImage2)
            val sum = result1 + result2

            if (sum == targetNumber) {
                resultTextView.text = "Félicitations ! Vous avez gagné ! La somme est $sum."
                startWinAnimation()
            } else {
                resultTextView.text = "Dommage ! Vous avez perdu. La somme est $sum."
            }
        }

        diceImage1.setOnClickListener(clickListener)
        diceImage2.setOnClickListener(clickListener)
    }

    private fun rollDice(diceImage: ImageView): Int {
        val dice = Dice(6)
        val diceRoll = dice.roll()
        val drawableResource = diceImages[diceRoll - 1]
        diceImage.setImageResource(drawableResource)
        return diceRoll
    }

    private fun startWinAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.shake)
        diceImage1.startAnimation(animation)
        diceImage2.startAnimation(animation)
        // Alternatively, use an animation with sparkles or other effects
    }
}


class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}