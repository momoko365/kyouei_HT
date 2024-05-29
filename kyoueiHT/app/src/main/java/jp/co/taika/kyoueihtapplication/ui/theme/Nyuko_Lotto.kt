package jp.co.taika.kyoueihtapplication.ui.theme

import Nyuko_Data_Save
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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

//Nyuko Locationからの遷移
class Nyuko_Lotto : ComponentActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //画面デザインとの紐づけ
        setContentView(R.layout.nyuko_lotto)

        //画面デザインの入力項目やボタンとの紐づけ
        var lotto_number = findViewById<EditText>(R.id.lotto_number)
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)
        var nyuko_Data = Nyuko_Data_Save
        var input_number = findViewById<TextView>(R.id.input_number)
        var selected_goods = findViewById<TextView>(R.id.selected_goods)

        //全画面からの引継ぎ
        val plan_num = intent.getIntExtra("plan_num", 0)
        val location_num = intent.getIntExtra("location_num", 0)

        var lotto_num: Int

        selected_goods.text = plan_num.toString()
        input_number.text = plan_num.toString()


        val nyukoDetail = NyukoDetail()
        var targetNyukoDetail: NyukoDetailData? = null

//次へボタンを推したときの処理
        next_btn.setOnClickListener {
            try {
//ロット番号が入力されている場合の処理
                if (lotto_number.text.toString().toInt() != null) {
                    runBlocking {
                        //スコープを呼び出す関数と同じにする
                        val job = CoroutineScope(Dispatchers.IO).launch {
                            //Workerクラスのインスタンスを使ってworkerの名前を取得する
                            targetNyukoDetail = nyukoDetail.getNyukoDetail(
                                Strings.NYUKODETAIL_URL,
                                plan_num
                            )

                        }
                        //コルーチン終了まで待つ
                        job.join()
                    }
                    //入庫予定明細のロット番号と入力されたロット番号が正しいか判定
                    if (targetNyukoDetail?.lotto_num?.toInt() == lotto_number.text.toString()
                            .toInt()
                    ) {
                        //正しい場合
                        lotto_num = lotto_number.text.toString().toInt()
                        val intent = Intent(this, Nyuko_Deadline::class.java)
                        intent.putExtra("plan_num", plan_num)
                        intent.putExtra("location_num", location_num)
                        intent.putExtra("lotto_num", lotto_num)
                        startActivity(intent)
                        finish()
                    } else {
                        //正しくない場合
                        val intent = Intent(this, Error_Message::class.java)
                        val message = "ロットが異なります"
                        intent.putExtra("ERROR_KEY", message)
                        startActivity(intent)
                    }
                }
//ロット番号が入力されていなかった場合の処理
            } catch (e: NumberFormatException) {
                val intent = Intent(this, Error_Message::class.java)
                val message = "入力漏れです"
                intent.putExtra("ERROR_KEY", message)
                startActivity(intent)
            }


        }

        //戻るボタンを押したときの処理
        back_btn.setOnClickListener {
            nyuko_Data.lotto = ""

            val intent = Intent(this, Nyuko_Location::class.java)
            intent.putExtra("plan_num", plan_num)


            startActivity(intent)
            finish()
        }
    }
}

