package com.ubaya.s160717023_uts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.startActivity

class ResultActivity : AppCompatActivity() {
    override fun onBackPressed() {
        // super.onBackPressed();
        // Not calling **super**, disables back button in current screen.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()

        setContentView(R.layout.activity_result)

        val playersScore = this.intent.getStringExtra("playersScore")
        var playersScoreArr = playersScore.split("&").toTypedArray()
        var score1 = playersScoreArr[0]
        var score2 = playersScoreArr[1]

        textViewScorePlayer1.setText(score1)
        textViewScorePlayer2.setText(score2)

        buttonNewGame.setOnClickListener {
            startActivity<MainActivity>(
                "id" to "1",
                "playersScore" to ""
            )
        }
    }
}
