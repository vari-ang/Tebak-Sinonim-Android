package com.ubaya.s160717023_uts

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TableRow
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.core.view.marginRight
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.textViewKata
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_player_start.*
import org.jetbrains.anko.editText
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class PlayerStartActivity : AppCompatActivity() {
    override fun onBackPressed() {
        // super.onBackPressed();
        // Not calling **super**, disables back button in current screen.
    }

    fun generateCorrectAnswer(sinonim:String, numOfCorrect:Int) {
        // remove all existing components
        TableRowCorrectAnswer.removeAllViewsInLayout()

        for(i in 1..sinonim.length) {
            var editText: EditText
            editText = EditText(this)
            TableRowCorrectAnswer.addView(editText)

            editText.setText(if(i <= numOfCorrect) "${sinonim[i-1]}" else " ")
            editText.gravity = Gravity.CENTER
            editText.isEnabled = false
            editText.isFocusable = false
            editText.layoutParams.width = 44
        }
    }

    // This function will be called by random button
    fun getRandomString(length: Int) : String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()

        setContentView(R.layout.activity_player_start)

        val id = this.intent.getStringExtra("id")
        val kata = this.intent.getStringExtra("kata")
        val sinonim = this.intent.getStringExtra("sinonim")
        var sinonimList = listOf<String>().toMutableList()
        var playersScore = this.intent.getStringExtra("playersScore")

        // Populate sinonim list with each character from sinonim
        for(i in 1..sinonim.length) { sinonimList.add(sinonim[i-1].toString()) }
        var numOfCorrect = 0
        var score = 100

        textViewSkor.text = "Skor: ${score}"
        textViewKata.text = kata

        generateCorrectAnswer(sinonim, 0)

        // populate sinonimArr
        while(sinonimList.size < 18) {
            var randomString = getRandomString(1)
            if(!sinonimList.contains(randomString)) {
                sinonimList.add(randomString)
            }
        }

        val shuffledSinonimList = sinonimList.shuffled()

        // Generate buttons
        for(i in 1..18) {
            var button: Button
            button = Button(this)

            if(i in 1..6) { TableRow1Random.addView(button) }
            if(i in 7..12) { TableRow2Random.addView(button) }
            if(i in 13..18) { TableRow3Random.addView(button) }

            button.setText("${shuffledSinonimList.elementAt(i-1)}")
            button.layoutParams.width = 60

            button.setOnClickListener {
                if(numOfCorrect != sinonim.length) {
                    val selectedText = (it as Button).text.toString()

                    // Correct answer
                    if (selectedText == sinonim[numOfCorrect].toString()) {
                        numOfCorrect += 1
                        generateCorrectAnswer(sinonim, numOfCorrect)

                        it.setBackgroundColor(Color.GREEN)
                        it.isEnabled = false

                        if(numOfCorrect == sinonim.length) {
                            if(id == "1") { playersScore = score.toString() }
                            if(id == "2") { playersScore += "&${score.toString()}"; buttonNextPlayer.setText("RESULTS") }
                            buttonNextPlayer.isVisible = true
                        }
                    }

                    // Wrong answer
                    else {
                        score -= 10
                        textViewSkor.text = "Skor: ${score}"
                    }
                }
            }
        }

        buttonNextPlayer.setOnClickListener {
            if(id == "1") {
                // Direct user to MainActivity for inputting questions to player 2
                startActivity<MainActivity>(
                    "id" to "2",
                    "playersScore" to playersScore
                )
            }
            else {
                // Direct user to ResultActivity
                startActivity<ResultActivity>(
                    "playersScore" to playersScore
                )
            }
        }
    }
}
