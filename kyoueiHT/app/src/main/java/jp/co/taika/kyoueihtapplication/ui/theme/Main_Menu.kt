package jp.co.taika.kyoueihtapplication.ui.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import jp.co.taika.kyoueihtapplication.R

class Main_Menu : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_mainmenu)
        var nyuko_btn = findViewById<Button>(R.id.nyuuko)
        var syukko_btn = findViewById<Button>(R.id.syukko)
        var in_move = findViewById<Button>(R.id.in_move)
        var invetory_btn = findViewById<Button>(R.id.inventory)
        var stok_btn = findViewById<Button>(R.id.stok)
        var back_btn = findViewById<Button>(R.id.back_btn)


        nyuko_btn.setOnClickListener {
            val intent = Intent(this, Souko_Input::class.java)
            startActivity(intent)
            finish()
        }

        syukko_btn.setOnClickListener {
            val intent = Intent(this, Souko_Input::class.java)
            startActivity(intent)
            finish()
        }

        in_move.setOnClickListener {
            val intent = Intent(this, Souko_Input::class.java)
            startActivity(intent)
            finish()
        }

        invetory_btn.setOnClickListener {
            val intent = Intent(this, Souko_Input::class.java)
            val flg = 1
            intent.putExtra("FLAG_KEY", flg)
            startActivity(intent)
            finish()
        }

        stok_btn.setOnClickListener {
            val intent = Intent(this, Souko_Input::class.java)
            startActivity(intent)
            finish()
        }



        back_btn.setOnClickListener {
            val intent = Intent(this, Worker_Name::class.java)
            startActivity(intent)
            finish()
        }


    }
}
