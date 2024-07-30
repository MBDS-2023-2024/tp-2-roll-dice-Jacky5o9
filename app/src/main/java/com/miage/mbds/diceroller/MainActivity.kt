package com.miage.mbds.diceroller

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.exemple.diceroller.R


class MainActivity : AppCompatActivity(){
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val rollButton : Button = findViewById(R.id.button)
        val resultTextView1:TextView = findViewById(R.id.textView1)
        val resultTextView2:TextView = findViewById(R.id.textView2)
        val messageTextView:TextView = findViewById(R.id.resultTextView)

        rollButton.setOnClickListener{
            val result1 = RollDice()
            val result2 = RollDice()

            resultTextView1.text = "Dé 1 : $result1"
            resultTextView2.text = "Dé 2 : $result2"

            if (result1 == result2){
                messageTextView.text = "Félicitations ! Vous avez gagné !"
            }else{
                messageTextView.text = "Vous avez perdu!"
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun RollDice() : Int{
        val dice = Dice(6)
        val diceRoll = dice.roll()
        return diceRoll
    }
}

class Dice(private val numSides : Int){
    fun roll(): Int {
        return (1..numSides).random()
    }
}