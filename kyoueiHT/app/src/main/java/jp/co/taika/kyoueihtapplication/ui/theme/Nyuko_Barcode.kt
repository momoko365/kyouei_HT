package jp.co.taika.kyoueihtapplication.ui.theme

import Nyuko_Data_Save
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import jp.co.taika.kyoueihtapplication.R
import jp.co.taika.kyoueihtapplication.function.Shohin
import jp.co.taika.kyoueihtapplication.function.ShohinData
import jp.co.taika.kyoueihtapplication.function.Strings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//Nyuko_Goodsからの遷移
class Nyuko_Barcode : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //画面デザインとの紐づけ
        setContentView(R.layout.nyuko_barcode)

        //画面デザインの入力項目やボタンとの紐づけ
        var puroduct_b = findViewById<EditText>(R.id.puroduct_b)
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)
        var output_number = findViewById<TextView>(R.id.output_number)

        var nyuko_Data = Nyuko_Data_Save

        val plan_num = intent.getIntExtra("plan_num", 0)

        output_number.text = plan_num.toString()

        val shohin = Shohin()


        var targetItem: ShohinData? = null


        //エンターキーを押すと画面遷移できるようにする
        fun handleKeyEvent(view: View, keyCode: Int): Boolean {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                next_btn.performClick()
                return true
            }
            return false
        }

        puroduct_b.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view, keyCode)

        }


//次へボタンを押したときの処理
        next_btn.setOnClickListener {

            try {//非同期スコープ内で処理を開始
                //テキストの取得
                //ユーザーから入力された数値がnullじゃないかどうか判定
                if (puroduct_b.text.toString().toLong() != null) {


                    runBlocking {
                        //スコープを呼び出す
                        val job = CoroutineScope(Dispatchers.IO).launch {
                            //Shohinクラスのインスタンスを使ってshohinバーコードのデータを取得
                            targetItem =
                                shohin.getShohin(Strings.SHOHIN_URL, plan_num)
                        }
                        //コルーチン終了まで待つ
                        job.join()
                    }


                    //targetNumberにtargetItemのバーコードと入力されたバーコードの数値が一致するかどうか
//                    val targetNumber =
//                        (targetItem?.jan?.toInt() == puroduct_b.text.toString().toInt())
                    if (targetItem?.jan?.toLong() == puroduct_b.text.toString().toLong()) {
                        val intent = Intent(this, Nyuko_Location::class.java)
                        intent.putExtra("plan_num", plan_num)
                        startActivity(intent)
                        finish()
                    } else {
                        val intent = Intent(this, Error_Message::class.java)
                        val message = "商品が異なります"
                        intent.putExtra("ERROR_KEY", message)
                        startActivity(intent)
                    }
                }
            } catch (e: NumberFormatException) {
                val intent = Intent(this, Error_Message::class.java)
                val message = "入力漏れです"
                intent.putExtra("ERROR_KEY", message)
                startActivity(intent)
            }
        }

        //戻るボタンを押したときの処理
        back_btn.setOnClickListener {
//            nyuko_Data.selected_goods = ""
            val intent = Intent(this, Nyuko_Goods::class.java)
            intent.putExtra("plan_num", plan_num)
            startActivity(intent)
            finish()
        }

    }
}
