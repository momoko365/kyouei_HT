package jp.co.taika.kyoueihtapplication.ui.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.journeyapps.barcodescanner.CaptureActivity
import jp.co.taika.kyoueihtapplication.R

class QR_MikenpinItem : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mikenpinitem)
        val next_btn =findViewById<Button>(R.id.next_btn)

        next_btn.setOnClickListener {
            val intent=Intent(this,QR_Kenpin_Naiyo::class.java)
        startActivity(intent)
        }

        val back_btn=findViewById<Button>(R.id.back_btn)
        back_btn.setOnClickListener {
            val intent =Intent(this,QR_Start_Inspection::class.java)
            startActivity(intent)
        }
    }

}