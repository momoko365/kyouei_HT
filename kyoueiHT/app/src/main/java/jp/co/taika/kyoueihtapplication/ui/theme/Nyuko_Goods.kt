package jp.co.taika.kyoueihtapplication.ui.theme

import Nyuko_Data_Save
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import jp.co.taika.kyoueihtapplication.R
import jp.co.taika.kyoueihtapplication.function.Nyuko
import jp.co.taika.kyoueihtapplication.function.NyukoData
import jp.co.taika.kyoueihtapplication.function.NyukoDetail
import jp.co.taika.kyoueihtapplication.function.NyukoDetailData
import jp.co.taika.kyoueihtapplication.function.NyukoHeader
import jp.co.taika.kyoueihtapplication.function.NyukoHeaderData
import jp.co.taika.kyoueihtapplication.function.Shohin
import jp.co.taika.kyoueihtapplication.function.ShohinData
import jp.co.taika.kyoueihtapplication.function.Strings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


//Nyuko Int Num からの遷移
class Nyuko_Goods : ComponentActivity() {
    private val nyukoHeader = NyukoHeader()
    private val nyukoDetail = NyukoDetail()
    private val shohin = Shohin()
    private val nyusyukkoData = Nyuko()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //画面デザインとの紐づけ
        setContentView(R.layout.nyuko_goods)

        //画面デザインの入力項目やボタンとの紐づけ
        var input_number = findViewById<TextView>(R.id.input_number)
        var zumi1 = findViewById<TextView>(R.id.zumi1)
        var zumi2 = findViewById<TextView>(R.id.zumi2)
        var plan1 = findViewById<TextView>(R.id.plan1)
        var plan2 = findViewById<TextView>(R.id.plan2)
        val goods_list: RadioGroup = findViewById(R.id.check_list)
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)
        var nyuko_Data = Nyuko_Data_Save

        //Nyuko Int NUm から渡された入庫番号を取得 null なら０とする
        //前の画面から渡された、入庫予定Noを基に、明細データの取得（APIへ問い合わせ）
        //		（どの商品　、いくつ入庫されるか？）
        val plan_num = intent.getIntExtra("plan_num", 0)


        var keep_date = arrayOf<String>()
        var keep_lank = arrayOf<Int>()
        var selected_date: String? = null

        var targetNyukoHeader: NyukoHeaderData? = null
        var targetNyukoDetail: NyukoDetailData? = null
        var targetShohin: ShohinData? = null
        var targetNyusyukkoData: ArrayList<NyukoData>? = null

        //プランNUMが指定されてきたら明細ほか取得処理
        if (plan_num != 0) {
            runBlocking {
                //スコープの呼び出し
                val job = CoroutineScope(Dispatchers.IO).launch {
                    //入庫予定伝票ヘッダ(NSKH)
                    targetNyukoHeader = nyukoHeader.getNyukoHeader(
                        Strings.NYUKOHEADER_URL,
                        plan_num
                    )
                    targetNyukoDetail =
                        nyukoDetail.getNyukoDetail(Strings.NYUKODETAIL_URL, plan_num)


                    targetShohin = shohin.getShohin(Strings.SHOHIN_URL, plan_num)
                    targetNyusyukkoData = nyusyukkoData.getNyuSyukko(
                        Strings.NYUSYUKKO_URL,
                        plan_num
                    )
                }
                //コルーチン終了まで待つ
                job.join()
            }

        }

        //plan_num の値を文字列に変換して、それを input_number のテキストとして設定
        input_number.text = plan_num.toString()

        //ラジオボタン作成
        var targetNumber = targetShohin?.item_key == targetNyukoDetail?.item_key
        if (targetNumber != null) {
            var goods = android.widget.RadioButton(this)
            goods.text = targetShohin?.item_key
            goods.setTextSize(TypedValue.COMPLEX_UNIT_PT, 18F)
            goods_list.addView(goods)

        }

        goods_list.setOnCheckedChangeListener { group, checkedId ->
            val selected_goods_name = group.findViewById<RadioButton>(checkedId)
            //goods_list の中から選択されたラジオボタンを特定
            for (index in 0 until goods_list.childCount) {
                //選択されたラジオボタンのテキストを取得
                val radioButton = goods_list.getChildAt(index) as? RadioButton
                //goods_list 内の各子ビュー（ラジオボタン）を反復処理して、選択されたラジオボタンと同じテキストを持つラジオボタンを見つける
                if (radioButton != null && radioButton.text == selected_goods_name.text) {
                    // 選択されたラジオボタンのインデックス（位置）を取得
                    val lank = group.indexOfChild(radioButton)
                    // keep_lank という配列（もしくはリスト）にそのインデックスを追加
                    var keeplank = keep_lank.toMutableList()
                    keeplank.add(lank)
                    keep_lank = keeplank.toTypedArray()
                }
            }

            //ラジオボタン選択した後に商品の情報をテキストボックスに入れるロジック
            zumi1.text = targetNyukoDetail?.nyusyukkazumi_quantity1
            zumi2.text = targetNyukoDetail?.nyusyukkazumi_quantity2
            plan1.text = targetNyukoDetail?.nyusyukka_quantity1
            plan2.text = targetNyukoDetail?.nyusyukka_quantity2
        }

        //次へボタンを押したときの処理
        next_btn.setOnClickListener {
            val id = goods_list.checkedRadioButtonId
            val selected_id = goods_list.findViewById<RadioButton>(id)
            try {
                nyuko_Data.selected_goods = selected_id.text.toString()
            } catch (e: NullPointerException) {
                nyuko_Data.selected_goods = null.toString()
            }
            if (nyuko_Data.selected_goods != "null") {
                val intent = Intent(this, Nyuko_Barcode::class.java)
                intent.putExtra("plan_num", plan_num)

                startActivity(intent)
                finish()
            }
        }

//戻るボタンを押したときの処理
        back_btn.setOnClickListener {
//            nyuko_Data.selected_goods = ""
            val intent = Intent(this, Nyuko_Int_Num::class.java)
            startActivity(intent)
            finish()
        }
    }
}

