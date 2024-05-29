package jp.co.taika.kyoueihtapplication.ui.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import jp.co.taika.kyoueihtapplication.R

class QR_Kenpin_Finish: ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //デザイン画面との紐づけ
        setContentView(R.layout.kenpinfinish)

        var back_btn = findViewById<Button>(R.id.next_btn)
//        var back_btn = findViewById<Button>(R.id.back_btn)



        back_btn.setOnClickListener {
            val intent= Intent(this,Start_Screen::class.java)
            startActivity(intent)
            finish()
        }
    }
}