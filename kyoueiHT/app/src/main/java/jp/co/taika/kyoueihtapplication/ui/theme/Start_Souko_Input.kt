package jp.co.taika.kyoueihtapplication.ui.theme

import Start_Data_Save
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.room.Room
import jp.co.taika.kyoueihtapplication.R

import jp.co.taika.kyoueihtapplication.function.SoukoData_R
import jp.co.taika.kyoueihtapplication.function.UserDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//Start Main Menuからの遷移
class Start_Souko_Input : Import_Json() {

    private lateinit var db:UserDataBase



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        this.db= Room.databaseBuilder(
//            applicationContext,
//            UserDataBase::class.java,
//            "taika.db"
//        ).build()
//        this.dao=this.db.soukoDao()
//
//        //画面デザインとの紐づけ
//        setContentView(R.layout.start_souko_input)
//
//        //画面デザインの入力項目やボタンとの紐づけ
//        var souko_number = findViewById<EditText>(R.id.souko_number)
//        var next_btn = findViewById<Button>(R.id.next_btn)
//        var back_btn = findViewById<Button>(R.id.back_btn)
//
//        var start = Start_Data_Save
//
//        //Start Main Menuから渡されたフラグを取得
//        val tana_flag = intent.getIntExtra("TANA_FLAG_KEY", 0)//1:棚卸
//        val flag = intent.getIntExtra("FLAG_KEY", 9)    //0:入庫 1:出庫
//
//        GlobalScope.launch {
//            withContext(Dispatchers.IO){
//                val souko=SoukoData_R(0,"nakae","murakami","horii")
//            }
//        }
//
//        // リターンキーを押した時のイベント定義
//        fun handleKeyEvent(view: View, keyCode: Int): Boolean {
//            if (keyCode == KeyEvent.KEYCODE_ENTER) {
//                next_btn.performClick()
//                return true
//            }
//            return false
//        }
//        // 入庫番号入力時のイベント処理
//        souko_number.setOnKeyListener { view, keyCode, _ ->
//            handleKeyEvent(view, keyCode)
//
//        }
//
//
//        //次へボタンを押したときの処理
//        next_btn.setOnClickListener {
//            try {//非同期スコープ内で処理を開始
//                //ユーザーに入力してもらった値をIntに変換
//                start.souko_code = souko_number.text.toString().toInt()
//
//                GlobalScope.launch {
//                    val targetsouko= withContext(Dispatchers.IO){
//                        dao.getSoukoAll(start.souko_code)
//                    }
//                    if (targetsouko != null){
//                        withContext(Dispatchers.Main){
//                            val intent = Intent(this@Start_Souko_Input, Start_Inventory_Day::class.java)
//                            startActivity(intent)
//                            finish()
//                        } } else if (flag == 0) {
//                        val intent = Intent(this@Start_Souko_Input, Nyuko_Int_Num::class.java)
//                        startActivity(intent)
//                        finish()
//                    } else if (flag == 1) {
//                        val intent = Intent(this@Start_Souko_Input, Syukko_Int_Num::class.java)
//                        startActivity(intent)
//                        finish()
//                    } else {
//                        val intent = Intent(this@Start_Souko_Input, Error_Message::class.java)
//                        val message = "コードの誤りです"
//                        intent.putExtra("ERROR_KEY", message)
//                        startActivity(intent)
////                    flaga = 1
//                    }
//                    }
//
//
//
//                    //等しい場合はStart_inventory_dayへ遷移
//
//
//            } catch (e: NumberFormatException) {
//                val intent = Intent(this, Error_Message::class.java)
//                val message = "必ず入力してください"
//                intent.putExtra("ERROR_KEY", message)
//                startActivity(intent)
//            }
//            var flaga = 0
//            if (flaga != 1) {
//                if (tana_flag == 1) {
//                    val intent = Intent(this, Start_Inventory_Day::class.java)
//                    startActivity(intent)
//                    finish()
//                } else if (flag == 0) {
//                    val intent = Intent(this, Nyuko_Int_Num::class.java)
//                    startActivity(intent)
//                    finish()
//                } else if (flag == 1) {
//                    val intent = Intent(this, Syukko_Int_Num::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//
//            }
//        }
//
//
////戻るボタンを押すとStartMainMenuへ遷移
//        back_btn.setOnClickListener {
//            val intent = Intent(this, Start_Main_Menu::class.java)
//            startActivity(intent)
//            finish()
//        }
    }
}

