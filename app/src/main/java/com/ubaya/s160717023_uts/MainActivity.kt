package com.ubaya.s160717023_uts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {
    override fun onBackPressed() {
        // super.onBackPressed();
        // Not calling **super**, disables back button in current screen.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)

        var currId = this.intent.getStringExtra("id")
        var id = if(!currId.isNullOrBlank()) currId else "1"

        var playersScore = this.intent.getStringExtra("playersScore")

        textViewPlayerQuestion.setText("Soal Untuk Player ${id}")
        textViewGivePhoneToPlayer.setText("Berikan HP ini ke Player ${id}")
        buttonStartPlayer.setText("Mulai Pemain ${id}")

        buttonStartPlayer.setOnClickListener {
            var isError = false
            val kata = editTextKata.text.toString()
            val sinonim = editTextSinonim.text.toString()

            // check character
            if(kata.isEmpty()) { isError = true; alert(Appcompat, "Isikan field kata").show() }
            if(sinonim.isEmpty()) { isError = true; alert(Appcompat, "Isikan field sinonim").show() }

            if(kata.length > 8) { isError = true; alert(Appcompat, "Panjang kata maximal 8 karakter").show() }
            if(sinonim.length > 8) { isError = true; alert(Appcompat, "Panjang sinonim maximal 8 karakter").show() }

            if(!kata.matches(Regex("^[a-zA-Z]*$"))) { isError = true; alert(Appcompat, "Field kata harus merupakan alfabet saja").show() }
            if(!sinonim.matches(Regex("^[a-zA-Z]*$"))) { isError = true; alert(Appcompat, "Field sinonim harus merupakan alfabet saja").show() }

            if(!isError) {
                startActivity<PlayerStartActivity>(
                    "id" to id,
                    "kata" to kata.toUpperCase(),
                    "sinonim" to sinonim.toUpperCase(),
                    "playersScore" to if(!playersScore.isNullOrBlank()) playersScore else ""
                )
            }
        }
    }
}
