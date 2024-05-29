package jp.co.taika.kyoueihtapplication.ui.theme

import Start_Data_Save
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import jp.co.taika.kyoueihtapplication.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


//作業日入力画面　Start_Screenから呼び出し
class Start_Work_Day : ComponentActivity(){
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_work_day)

        //入力項目と変数の紐づけ
        var work_year = findViewById<EditText>(R.id.work_year)
        var work_month = findViewById<EditText>(R.id.work_month)
        var work_day = findViewById<EditText>(R.id.work_day)
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)

//初期値の日付の生成
        var timesave = LocalDateTime.now()
        var getyear = DateTimeFormatter.ofPattern("yyyy")
        var getmonth = DateTimeFormatter.ofPattern("MM")
        var getday = DateTimeFormatter.ofPattern("dd")
        var year = getyear.format(timesave)
        var month = getmonth.format(timesave)
        var day = getday.format(timesave)
        var start = Start_Data_Save

        //生成した日付を入力項目に初期値としてセットする
        work_year.setText(year.toString())
        work_month.setText(month.toString())
        work_day.setText(day.toString())

        //次へボタンがおされた時の処理
        next_btn.setOnClickListener {
            try {
                //入力された年月日の取得
                start.work_year = work_year.text.toString().toInt()
                start.work_month = work_month.text.toString().toInt()
                start.work_day = work_day.text.toString().toInt()

                //入力された年月日のチェック
                if ((start.work_month <= 12 && start.work_month >= 1) && (start.work_day <= 31 && start.work_day >= 1)) {
                    //年月日チェックで成功の時（正しい年月日が入力されている場合）
                    val intent = Intent(this, Start_Worker_Name::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    //年月日チェックでエラーの時
                    val intent = Intent(this, Error_Message::class.java)
                    val message = "日付の誤りです"
                    intent.putExtra("ERROR_KEY", message)
                    startActivity(intent)
                }
            } catch (e: NumberFormatException) {
                //処理時に例外が発生した場合
                val intent = Intent(this, Error_Message::class.java)
                val message = "入力漏れです"
                intent.putExtra("ERROR_KEY", message)
                startActivity(intent)

            }
        }

        //戻るボタンを押した時の処理
        //Start_Screenへ遷移
        back_btn.setOnClickListener {
            val intent = Intent(this, Start_Screen::class.java)
            startActivity(intent)
            finish()

        }

    }
}
