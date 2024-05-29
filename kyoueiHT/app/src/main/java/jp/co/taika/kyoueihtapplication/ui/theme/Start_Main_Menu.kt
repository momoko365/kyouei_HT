package jp.co.taika.kyoueihtapplication.ui.theme

import Start_Data_Save
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import jp.co.taika.kyoueihtapplication.R

//Start_Worker_Name からの遷移
class Start_Main_Menu : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //画面デザインとの紐づけ
        setContentView(R.layout.start_mainmenu)

        //画面デザインの入力項目やボタンとの紐づけ
        var nyuko_btn = findViewById<Button>(R.id.nyuuko)
        var syukko_btn = findViewById<Button>(R.id.syukko)
        var in_move = findViewById<Button>(R.id.in_move)
        var invetory_btn = findViewById<Button>(R.id.inventory)
        var stok_btn = findViewById<Button>(R.id.stok)
        var back_btn = findViewById<Button>(R.id.back_btn)
        var start = Start_Data_Save
        var QR_btn = findViewById<Button>(R.id.qr)

        //入庫ボタンを押したときの処理
        nyuko_btn.setOnClickListener {
            start.nyusyukko_judge = 0
            val intent = Intent(this, Start_Souko_Input::class.java)
            val flg = 0
            intent.putExtra("FLAG_KEY", flg)
            startActivity(intent)
            finish()
        }

        //出庫ボタンを押したときの処理
        syukko_btn.setOnClickListener {
            start.nyusyukko_judge = 1
            val intent = Intent(this, Start_Souko_Input::class.java)
            val flg = 1
            intent.putExtra("FLAG_KEY", flg)
            startActivity(intent)
            finish()
        }

        //在庫移動を押したときの処理
        in_move.setOnClickListener {
            val intent = Intent(this, Start_Souko_Input::class.java)
            startActivity(intent)
            finish()
        }

        //棚卸を押したときの処理
        invetory_btn.setOnClickListener {
            val intent = Intent(this, Start_Souko_Input::class.java)
            val flg = 1
            intent.putExtra("TANA_FLAG_KEY", flg)
            startActivity(intent)
            finish()
        }

        //在庫参照を押した時の処理
        stok_btn.setOnClickListener {
            val intent = Intent(this, Start_Souko_Input::class.java)
            startActivity(intent)
            finish()
        }
        QR_btn.setOnClickListener {
            val intent = Intent(this, QRCodeMain::class.java)
            startActivity(intent)
            finish()
        }

        //戻るボタンを押したときの処理
        back_btn.setOnClickListener {
            val intent = Intent(this, Start_Worker_Name::class.java)
            startActivity(intent)
            finish()
        }

    }
}
