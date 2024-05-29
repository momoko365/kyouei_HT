package jp.co.taika.kyoueihtapplication.ui.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
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
import java.text.SimpleDateFormat
import java.util.Date

class Nyuko_Deadline : ComponentActivity() {

    //Nyuko_lottoからの遷移
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //画面デザインとの紐づけ
        setContentView(R.layout.nyuko_deadline_in)

        //レイアウト画面との紐づけ
        var plan_deadline = findViewById<TextView>(R.id.plan_deadline)
        var deadline_year = findViewById<EditText>(R.id.deadline_year)
        var deadline_month = findViewById<EditText>(R.id.deadline_month)
        var deadline_day = findViewById<EditText>(R.id.deadline_day)
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)
        var output_number = findViewById<TextView>(R.id.output_number)
        var selected_goods = findViewById<TextView>(R.id.selected_goods)


        //全画面からの引継ぎ
        val plan_num = intent.getIntExtra("plan_num", 0)
        val location_num = intent.getIntExtra("location_num", 0)
        val lotto_num = intent.getIntExtra("lotto_num", 0)


        var deadline: String

        output_number.text = plan_num.toString()
        selected_goods.text = plan_num.toString()


        val nyukoDetail = NyukoDetail()
        var targetNyukoDetail: NyukoDetailData? = null


        // EditTextのリスナーをセットアップ
        deadline_year.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) { // Enterキーが押されたら
                deadline_month.requestFocus() // 次のEditTextにフォーカスを移動
                return@setOnEditorActionListener true // イベントを処理済みとして返す
            }
            false
        }

        deadline_month.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                deadline_day.requestFocus()
                return@setOnEditorActionListener true
            }
            false
        }
        //エンターキーを押すと画面遷移できるようにする
        fun handleKeyEvent(view: View, keyCode: Int): Boolean {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                next_btn.performClick()
                return true
            }
            return false
        }
        deadline_day.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view, keyCode)

        }


//次へボタン推したときの処理
        next_btn.setOnClickListener {
            var deadline_year_num: Int
            var deadline_month_num: Int
            var deadline_day_num: Int
            runBlocking {
                //スコープの呼び出し
                val job = CoroutineScope(Dispatchers.IO).launch {

                    //入庫予定明細(NSKDH)
                    targetNyukoDetail = nyukoDetail.getNyukoDetail(
                        Strings.NYUKODETAIL_URL,
                        plan_num
                    )

                }
                //コルーチン終了まで待つ
                job.join()
            }
            // 入力された年月日を取得し、整数に変換する
            try {
                deadline_year_num = deadline_year.text.toString().toInt()
            } catch (e: NumberFormatException) {
                deadline_year_num = 0
            }
            try {
                deadline_month_num = deadline_month.text.toString().toInt()
            } catch (e: NumberFormatException) {
                deadline_month_num = 0
            }
            try {
                deadline_day_num = deadline_day.text.toString().toInt()
            } catch (e: NumberFormatException) {
                deadline_day_num = 0
            }

            // 日付のフォーマットパターンを指定
            val pattern = "yyyy/MM/dd"

// SimpleDateFormatオブジェクトを作成
            val sdf = SimpleDateFormat(pattern)
            // 年月日の整数値から日付文字列を作成
            val dateString_text = "$deadline_year_num/$deadline_month_num/$deadline_day_num"

            val dateString = targetNyukoDetail?.deadline_date
            val format = SimpleDateFormat("yyyy/MM/dd")
            val detail_deadline_date: Date? = format.parse(dateString)


            // 日付文字列をDateオブジェクトにパース
            val date: Date? = sdf.parse(dateString_text)

            if (date == detail_deadline_date) {
                deadline =
                    deadline_year_num.toString() + "/" + deadline_month_num.toString() + "/" + deadline_day_num.toString()
                val intent = Intent(this, Nyuko_Quantity_Int::class.java)
                intent.putExtra("plan_num", plan_num)
                intent.putExtra("location_num", location_num)
                intent.putExtra("lotto_num", lotto_num)
                intent.putExtra("deadline", deadline)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, Error_Message::class.java)
                val message = "日付の誤りです"
                intent.putExtra("ERROR_KEY", message)
                startActivity(intent)
            }


        }

        back_btn.setOnClickListener {
//            nyuko_Data.deadline_date = ""

            val intent = Intent(this, Nyuko_Lotto::class.java)
            intent.putExtra("plan_num", plan_num)
            intent.putExtra("location_num", location_num)
            startActivity(intent)
            finish()
        }

    }
}
