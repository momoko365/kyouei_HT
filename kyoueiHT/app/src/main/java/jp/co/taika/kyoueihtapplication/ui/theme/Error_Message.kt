package jp.co.taika.kyoueihtapplication.ui.theme

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import jp.co.taika.kyoueihtapplication.R

//
class Error_Message : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.error_message)
        var message = findViewById<TextView>(R.id.message)
        var back_btn = findViewById<Button>(R.id.back_btn)

        message.text = intent.getStringExtra("ERROR_KEY")

        back_btn.setOnClickListener {
            finish()
        }
    }
}
