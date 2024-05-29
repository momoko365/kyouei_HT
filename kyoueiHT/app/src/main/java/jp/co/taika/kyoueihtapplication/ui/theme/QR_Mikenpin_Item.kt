package jp.co.taika.kyoueihtapplication.ui.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import jp.co.taika.kyoueihtapplication.R

class QR_Mikenpin_Item  : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mikenpinitem)
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)

        val shohinId = intent.getStringExtra("ShohinCD") ?: "データなしです"

        next_btn.setOnClickListener {
            val intent= Intent(this,QR_Start_Inspection::class.java)
            startActivity(intent)
            finish()
        }

        back_btn.setOnClickListener {
            val intent=Intent(this,Nyuko_Quantity_Int::class.java)
            intent.putExtra("ShohinCD",shohinId)
            startActivity(intent)
        }

    }

}