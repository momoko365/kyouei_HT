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
import jp.co.taika.kyoueihtapplication.function.NyukoDetail
import jp.co.taika.kyoueihtapplication.function.NyukoDetailData
import jp.co.taika.kyoueihtapplication.function.Strings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//Nyuko_Barcodeからの遷移
class Nyuko_Location : ComponentActivity() {
  

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //画面デザインとの紐づけ
        setContentView(R.layout.nyuko_location)

        //画面デザインの入力項目との紐づけ
        var location_number = findViewById<EditText>(R.id.location_number)
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)
        var input_number = findViewById<TextView>(R.id.input_number)
        var zan_number = findViewById<TextView>(R.id.plan_num)
        var selected_goods = findViewById<TextView>(R.id.selected_goods)

        var nyuko_Data = Nyuko_Data_Save

        val plan_num = intent.getIntExtra("plan_num", 0)


        val nyukoDetail = NyukoDetail()
        var targetNyukoDataDetail: NyukoDetailData? = null
        input_number.text = plan_num.toString()

        var location_num: Int


        //エンターキーを押すと画面遷移できるようにする
        fun handleKeyEvent(view: View, keyCode: Int): Boolean {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                next_btn.performClick()
                return true
            }
            return false
        }

        location_number.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view, keyCode)

        }

        runBlocking {
            //スコープの呼び出し
            val job = CoroutineScope(Dispatchers.IO).launch {
                //NyukoDetailクラスのインスタンスを使って入庫予定明細のデータを取得
                targetNyukoDataDetail =
                    nyukoDetail.getNyukoDetail(Strings.NYUKODETAIL_URL, plan_num)
            }
            //コルーチン終了まで待つ
            job.join()
        }


        if (targetNyukoDataDetail != null) {
            // nyusyukka_quantity1 および nyusyukkazumi_quantity1 が空でないことを確認する
            val total1 =
                if (targetNyukoDataDetail!!.nyusyukka_quantity1.isNotEmpty() && targetNyukoDataDetail!!.nyusyukkazumi_quantity1.isNotEmpty()) {
                    targetNyukoDataDetail!!.nyusyukka_quantity1.toInt() - targetNyukoDataDetail!!.nyusyukkazumi_quantity1.toInt()
                } else {
                    // 空の場合は適切な処理を行うか、エラー処理を行う
                    // 例えば、デフォルト値を設定するなど
                    // ここでは 0 を設定する
                    "null"
                }
            // nyusyukka_quantity2 および nyusyukkazumi_quantity2 が空でないことを確認する
            val total2 =
                if (targetNyukoDataDetail!!.nyusyukka_quantity2.isNotEmpty() && targetNyukoDataDetail!!.nyusyukkazumi_quantity2.isNotEmpty()) {
                    targetNyukoDataDetail!!.nyusyukka_quantity2.toInt() - targetNyukoDataDetail!!.nyusyukkazumi_quantity2.toInt()
                } else {
                    // 空の場合は適切な処理を行うか、エラー処理を行う
                    // 例えば、デフォルト値を設定するなど
                    // ここでは 0 を設定する
                    "null"
                }
            val result = "$total1/$total2"

            zan_number.text = result
        }
        //次へボタンを押したときの処理
        next_btn.setOnClickListener {

            if (location_number.text.toString()
                    .toInt() != null
            ) {
                if (targetNyukoDataDetail!!.location.toInt() == location_number.text.toString()
                        .toInt()
                ) {
                    location_num = location_number.text.toString().toInt()
                    val intent = Intent(this, Nyuko_Lotto::class.java)
                    intent.putExtra("plan_num", plan_num)
                    intent.putExtra("location_num", location_num)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this, Error_Message::class.java)
                    val message = "ロケーションの誤りです"
                    intent.putExtra("ERROR_KEY", message)
                    startActivity(intent)
                }

            } else {
                val intent = Intent(this, Error_Message::class.java)
                val message = "必ず入力してください"
                intent.putExtra("ERROR_KEY", message)
                startActivity(intent)
            }


        }

        //戻るボタンを押したときの処理
        back_btn.setOnClickListener {
//            nyuko_Data.selected_goods = ""
            val intent = Intent(this, Nyuko_Barcode::class.java)
            intent.putExtra("plan_num", plan_num)
            startActivity(intent)
            finish()
        }
    }
}
