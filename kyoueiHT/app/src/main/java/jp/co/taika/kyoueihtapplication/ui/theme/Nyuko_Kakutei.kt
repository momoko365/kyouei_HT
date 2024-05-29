package jp.co.taika.kyoueihtapplication.ui.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import jp.co.taika.kyoueihtapplication.R
import jp.co.taika.kyoueihtapplication.function.NyukoDetail
import jp.co.taika.kyoueihtapplication.function.NyukoDetailData
import jp.co.taika.kyoueihtapplication.function.PutNyukoDetail
import jp.co.taika.kyoueihtapplication.function.PutNyukoDetailData
import jp.co.taika.kyoueihtapplication.function.Strings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//Nyuko Quantity からの遷移
class Nyuko_Kakutei : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //画面デザイン都の紐づけ
        setContentView(R.layout.nyuko_kakutei)

        //画面デザインの入力項目やボタンとの紐づけ
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)
        var input_number = findViewById<TextView>(R.id.input_number)
        var goods = findViewById<TextView>(R.id.goods)
        var location_number = findViewById<TextView>(R.id.location_number)
        var lotto_number = findViewById<TextView>(R.id.lotto_number)
        var deadline = findViewById<TextView>(R.id.deadline)
        var number = findViewById<TextView>(R.id.number)


        //全画面からデータの引継ぎ
        val plan_num = intent.getIntExtra("plan_num", 0)
        val location_num = intent.getIntExtra("location_num", 0)
        val lotto_num = intent.getIntExtra("lotto_num", 0)
        val deadline_text = intent.getStringExtra("deadline")
        val quantity_usertext_1 = intent.getIntExtra("quantity_usertext_1", 0)
        val quantity_usertext_2 = intent.getIntExtra("quantity_usertext_2", 0)

        val nyukoDetail = NyukoDetail()
        val putNyukoDetail = PutNyukoDetail()
        var targetNyukoDetail: NyukoDetailData? = null
        var targetPutNyukodetail: PutNyukoDetailData? = null


        runBlocking {
            //スコープの呼び出し
            val job = CoroutineScope(Dispatchers.IO).launch {
                targetNyukoDetail =
                    nyukoDetail.getNyukoDetail(Strings.NYUKODETAIL_URL, plan_num)
            }
            //コルーチン終了まで待つ
            job.join()
        }

        //それぞれのテキストにこれまでの画面でユーザーが入力してきた数値を入れる
        lotto_number.text = targetNyukoDetail?.lotto_num
        location_number.text = targetNyukoDetail?.location
        input_number.text = plan_num.toString()
        goods.text = plan_num.toString()
        deadline.text = targetNyukoDetail?.deadline_date
        number.text =
            quantity_usertext_1.toString() + "/" + quantity_usertext_2.toString()


        //確定ボタンを押したときの処理
        next_btn.setOnClickListener {
            runBlocking {
                //スコープの呼び出し
                //PUTで書き込み
                val job = CoroutineScope(Dispatchers.IO).launch {
                    targetPutNyukodetail =
                        putNyukoDetail.getPutNyukoDetail(
                            Strings.PUTNYUKODETAIL_URL,
                            plan_num,
                            location_num,
                            lotto_num,
                            deadline_text,
                            quantity_usertext_1,
                            quantity_usertext_2
                        )
                }
                //コルーチン終了まで待つ
                job.join()
            }

            //入庫残をそれぞれの変数に代入
            var nyukoOK_quantity_1 =
                targetNyukoDetail?.nyusyukka_quantity1!!.toInt() - targetNyukoDetail?.nyusyukkazumi_quantity1!!.toInt()
            var nyukoOK_quantity_2 =
                targetNyukoDetail?.nyusyukka_quantity2!!.toInt() - targetNyukoDetail?.nyusyukkazumi_quantity2!!.toInt()


            //入庫残>0の時、Nyuko_Locationに戻り入庫残を再表示
            if (nyukoOK_quantity_1 < 0 || nyukoOK_quantity_2 < 0) {
                val intent = Intent(this, Nyuko_Location::class.java)
                intent.putExtra("plan_num", plan_num)
                startActivity(intent)
                finish()

            }
            //全品入庫済み時、Nyuko_Int_Numに戻り画面クリア
            else if (nyukoOK_quantity_1 == 0 || nyukoOK_quantity_2 == 0) {

                val intent = Intent(this, Nyuko_Int_Num::class.java)
                startActivity(intent)
                finish()

                //入庫残＝0の時、Nyuko_Goodsに戻り次商品を表示
            } else if (targetNyukoDetail?.nyusyukka_quantity1!!.toInt() == 0 || targetNyukoDetail?.nyusyukka_quantity2!!.toInt() == 0) {


                val intent = Intent(this, Nyuko_Goods::class.java)
                intent.putExtra("plan_num", plan_num)
                startActivity(intent)
                finish()
            } else      // targetPutNyukodetailがnullでないかつ正常なレスポンスが返ってきたかどうかを判定
                if (targetPutNyukodetail?.rc == "0") {
                    // 画面遷移するなどの処理を行う
                    val intent = Intent(this, Start_Main_Menu::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    // PUTリクエストが失敗した場合の処理

                    val intent = Intent(this, Nyuko_Int_Num::class.java)
                    startActivity(intent)
                    finish()
                }

        }


        //戻るボタンを押した時の処理
        back_btn.setOnClickListener {

            val intent = Intent(this, Nyuko_Quantity_Int::class.java)
            intent.putExtra("plan_num", plan_num)
            intent.putExtra("location_num", location_num)
            intent.putExtra("lotto_num", lotto_num)
            intent.putExtra("deadline", deadline_text)
            startActivity(intent)
            finish()
        }
    }
}

