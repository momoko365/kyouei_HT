package jp.co.taika.kyoueihtapplication.ui.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import jp.co.taika.kyoueihtapplication.R

class Souko_Input : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.souko_input)
        var souko_number = findViewById<EditText>(R.id.souko_number)
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)
        val flag = intent.getIntExtra("FLAG_KEY", 0)

        //エンターキーを押すと画面遷移できるようにする
        //これなぜかできない
        fun handleKeyEvent(view: View, keyCode: Int): Boolean {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
//                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)
                next_btn.performClick()
                return true
            }
            return false
        }

        souko_number.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view, keyCode)
        }

        next_btn.setOnClickListener {
            if (flag == 1) {
                val intent = Intent(this, Inventory_Day::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, Nyuko_Int_Num::class.java)
                startActivity(intent)
                finish()
            }
        }

        back_btn.setOnClickListener {
            val intent = Intent(this, Main_Menu::class.java)
            startActivity(intent)
            finish()
        }
    }
}

