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
import jp.co.taika.kyoueihtapplication.function.NyukoHeader
import jp.co.taika.kyoueihtapplication.function.NyukoHeaderData
import jp.co.taika.kyoueihtapplication.function.Strings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//Start_Souko_inputからの遷移


// 入庫予定Noの入力を求めて、入力されたNoが存在するかAPIに問い合わせる処理画面
class Nyuko_Int_Num : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //画面デザインとの紐づけ
        setContentView(R.layout.nyuko_int_num)

        //ユーザーが入力した数値を変数Int_Numberに代入
        var Int_Number = findViewById<EditText>(R.id.number)
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)


        var targetNyukoHeader: NyukoHeaderData? = null
        val nyukoHeader = NyukoHeader()

        var plan_num: Int


        //エンターキーで画面遷移する
        fun handleKeyEvent(view: View, keyCode: Int): Boolean {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                next_btn.performClick()
                return true
            }
            return false
        }
        //入庫番号入力時のイベント処理
        Int_Number.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view, keyCode)
        }


//        //次へボタンを押したときの処理（入庫予定Noの入力を受け取って、APIに問い合わせ、存在するか確認
        next_btn.setOnClickListener {

            try {//Intに格納できる最大値かどうかでトライキャッチ
                if (Int_Number.text.toString().toInt() <= 2147483647) {
                    plan_num = Int_Number.text.toString().toInt()
                } else {
                    plan_num = 0
                }
            } catch (e: NumberFormatException) {
                plan_num = 0
            }
            runBlocking {
                //スコープを呼び出す関数と同じにする
                val job = CoroutineScope(Dispatchers.IO).launch {

                    targetNyukoHeader = nyukoHeader.getNyukoHeader(
                        Strings.NYUKOHEADER_URL,
                        plan_num
                    )
                }
                //コルーチン終了まで待つ
                job.join()
            }
            //apiコール後の結果で入出庫番号が帰ってこなかったらエラー
            if (targetNyukoHeader?.nyusyukko_num.isNullOrEmpty()) {
                val intent = Intent(this, Error_Message::class.java)
                val message = "入出庫番号が見つかりません"
                intent.putExtra("ERROR_KEY", message)
                startActivity(intent)
            } else {
                val intent = Intent(this, Nyuko_Goods::class.java)
                //次の画面に入庫番号を渡して開く
                intent.putExtra("plan_num", plan_num)
                startActivity(intent)
                finish()

            }
        }


        //戻るボタンを押したときの処理
        back_btn.setOnClickListener {

            val intent = Intent(this, Start_Souko_Input::class.java)
            val flg = 0
            intent.putExtra("FLAG_KEY", flg)

            startActivity(intent)
        }
    }
}






