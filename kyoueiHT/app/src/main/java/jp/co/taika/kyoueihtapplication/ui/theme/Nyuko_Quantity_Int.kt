package jp.co.taika.kyoueihtapplication.ui.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import jp.co.taika.kyoueihtapplication.R
import jp.co.taika.kyoueihtapplication.function.NyukoDetail
import jp.co.taika.kyoueihtapplication.function.NyukoDetailData
import jp.co.taika.kyoueihtapplication.function.QRDatalistdAO
import jp.co.taika.kyoueihtapplication.function.ShohinDAO
import jp.co.taika.kyoueihtapplication.function.Strings
import jp.co.taika.kyoueihtapplication.function.UserDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

//QR_Start_Inspectionからの遷移
class Nyuko_Quantity_Int : ComponentActivity() {
    //データベース、daoのインスタンスを遅延初期化
    private lateinit var db: UserDataBase
    private lateinit var palletdao: ShohinDAO
    private lateinit var qrCodeListDao: QRDatalistdAO

    private var kazu: Int? = null
    private var mikenpinkazu: Int? = null
    private var shohinId: String? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //デザイン画面との紐づけ
        setContentView(R.layout.nyuko_quantity_int)
        // データベースの初期化とビルドを非同期で行う
        GlobalScope.launch {
            // データベースの初期化処理
            withContext(Dispatchers.IO) {
                db = Room.databaseBuilder(
                    applicationContext,
                    UserDataBase::class.java,
                    "taika.db"
                ).fallbackToDestructiveMigration().build()

                // DAOの初期化
                palletdao = db.shohindao()
                qrCodeListDao = db.qrdatalistdao()
//前画面からの商品コードを受け継ぐ
                shohinId = intent.getStringExtra("ShohinCD") ?: "データなしです"
                //商品コードに対応する行をパレットQRから探す
                val palletData = shohinId.let { palletdao.getShohinCD(shohinId!!) }
                //商品コードに対応する行を検品リストから探す
                val qrCodedata = shohinId.let { qrCodeListDao.getShohinCD(shohinId!!) }
                withContext(Dispatchers.Main) {
                    // null安全な乗算処理
                    kazu = qrCodedata?.suryo?.toIntOrNull()?.let { inQ ->
                        qrCodedata.suryo?.toIntOrNull()?.let { caseQ ->
                            inQ * caseQ
                        }

                    }
                    mikenpinkazu = qrCodedata?.let { kazu?.minus(it.zumi) }
//パレットQRのケー数をテキストビューに表示させる
                    findViewById<TextView>(R.id._int).text = palletData.case_q ?: "0"
//                    findViewById<TextView>(R.id.next_to_int).text=palletData.in_q ?:"0"
                    //商品名を表示させる
                    findViewById<TextView>(R.id.output_number).text = qrCodedata?.item_name
                    findViewById<TextView>(R.id.number).text = qrCodedata?.suryo ?: "データがありません"
//                    findViewById<TextView>(R.id.selected_goods).text = shohinData.item_name ?: "情報なし"
                }
            }
        }


        //デザインの入力項目やボタンとの紐づけ
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)

        //検品する数量入力テキスト
        var quantity_user = findViewById<EditText>(R.id.nexttoint)

        // 5桁までの入力制限を設けるInputFilter
        val lengthFilter = InputFilter.LengthFilter(5)
        // フィルターをセット
        quantity_user.filters = arrayOf(lengthFilter)

        //エンターキーを押すと画面遷移できるようにする
        fun handleKeyEvent(view: View, keyCode: Int): Boolean {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                next_btn.performClick()
                return true
            }
            return false
        }
        quantity_user.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view, keyCode)
        }


        next_btn.setOnClickListener {
            val userInput = quantity_user.text.toString().toIntOrNull() ?: 0 // EditTextからの入力を取得、nullの場合は0を使用
            GlobalScope.launch(Dispatchers.IO) {
                // 商品コードに対応する行をデータベースから探す
                val palletData = shohinId?.let { palletdao.getShohinCD(it) }
                val qrCodedata = shohinId?.let { qrCodeListDao.getShohinCD(it) }

                if (palletData != null && qrCodedata != null) {
                    withContext(Dispatchers.Main) {
                        val zumi = qrCodedata?.zumi ?: 0

                        val suryo = qrCodedata?.suryo?.toIntOrNull()
                            ?: Int.MAX_VALUE // 大きな値をデフォルトにして、比較に失敗する確率を低くする
                        val caseQ = palletData.case_q.toIntOrNull() ?: 0
                        val inQ = palletData.in_q.toIntOrNull() ?: 0

                        if (zumi + userInput+(caseQ * inQ) > suryo) {
                            val intent = Intent(this@Nyuko_Quantity_Int, Error_Message::class.java)
                            intent.putExtra("ERROR_KEY", "検品数を超えています")
                            startActivity(intent)

                        } else if (zumi + userInput+ (caseQ * inQ) <= suryo) {
                            GlobalScope.launch(Dispatchers.IO) {
                                shohinId?.let { it1 ->
                                    qrCodeListDao.updateZumi(
                                        zumi +userInput+ (caseQ * inQ),
                                        it1
                                    )
                                }
                                // 全行チェック
                                val updatedDataList = qrCodeListDao.getAll()
                                val allMatched = updatedDataList.all { it.zumi == it.suryo?.toIntOrNull() }
                                withContext(Dispatchers.Main) {
                                    if (allMatched) {
                                        val intent = Intent(
                                            this@Nyuko_Quantity_Int,
                                            QR_Kenpin_Finish::class.java
                                        )
                                        startActivity(intent)
                                    } else {


                                        val intent = Intent(
                                            this@Nyuko_Quantity_Int,
                                            QR_Start_Inspection::class.java
                                        )
                                        startActivity(intent)
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }

        back_btn.setOnClickListener {
            val intent=Intent(this@Nyuko_Quantity_Int,Start_Screen::class.java)
            startActivity(intent)
        }

//        //レイアウト画面との紐づけ
//
////検品する数量入力テキスト
////        var quantity_user = findViewById<EditText>(R.id._int)
//
//
////ユーザーが入力する数値を初期化
//
//        var quantity_usertext: Int
//
//        //エンターキーを押すと画面遷移できるようにする
//        fun handleKeyEvent(view: View, keyCode: Int): Boolean {
//            if (keyCode == KeyEvent.KEYCODE_ENTER) {
//                next_btn.performClick()
//                return true
//            }
//            return false
//        }
//        quantity_user.setOnKeyListener { view, keyCode, _ ->
//            handleKeyEvent(view, keyCode)
//        }
//
//        //次へボタンを押したときの処理
//        next_btn.setOnClickListener {
//
//            val inputText = quantity_user.text.toString()
//            if (inputText.isEmpty()) {
//                // 入力が空の場合、エラーメッセージ画面に遷移
//                val intent = Intent(this, Error_Message::class.java)
//                intent.putExtra("ERROR_KEY", "入力が必要です")
//                startActivity(intent)
//                return@setOnClickListener
//            }
//            try {
//                val quantity_usertext = inputText.toInt()
//                GlobalScope.launch {
//                    // DBから全データを取得
//                    val dataList = db.qrdatalistdao().getAll()
//                    // 前画面で取得した商品CDを引数にして商品CDに対応する行の取得を行う
//                    val qrCodedata = shohinId?.let { qrCodeListDao.getShohinCD(it) }
//                    // 新しいzumiの計算
//                    val newTotal = if (quantity_usertext < 0) {
//                        // 入力が負の値の場合、zumiから減算
//                        (qrCodedata?.zumi ?: 0) + quantity_usertext
//                    } else {
//                        // 入力が正の値の場合、zumiに加算
//                        (qrCodedata?.zumi ?: 0) + quantity_usertext
//                    }
//
//                    withContext(Dispatchers.Main) {
//                        if (newTotal < 0) {
//                            // 更新後のzumiが0未満になる場合、エラー
//                            val intent = Intent(this@Nyuko_Quantity_Int, Error_Message::class.java)
//                            intent.putExtra("ERROR_KEY", "検品数量が不正です")
//                            startActivity(intent)
//                        } else if (newTotal > kazu!!) {
//                            // 検品数量が出荷数を超える場合、エラー
//                            val intent = Intent(this@Nyuko_Quantity_Int, Error_Message::class.java)
//                            intent.putExtra("ERROR_KEY", "検品数を超えています")
//                            startActivity(intent)
//                        } else {
//                            // DBの更新
//                            GlobalScope.launch(Dispatchers.IO) {
//                                shohinId?.let {
//                                    qrCodeListDao.updateZumi(zumi = newTotal, shohinCD = it)
//                                }
//                                // DB更新後に全行をチェック
//                                val updatedDataList = db.qrdatalistdao().getAll()
//                                val allMatched = updatedDataList.all { it.zumi == kazu }
//                                withContext(Dispatchers.Main) {
//                                    if (allMatched) {
//                                        val intent = Intent(
//                                            this@Nyuko_Quantity_Int,
//                                            QR_Kenpin_Finish::class.java
//                                        )
//                                        startActivity(intent)
//                                    } else {
//                                        val intent = Intent(
//                                            this@Nyuko_Quantity_Int,
//                                            QR_Mikenpin_Item::class.java
//                                        )
//                                        intent.putExtra("ShohinCD", shohinId)
//                                        startActivity(intent)
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            } catch (e: NumberFormatException) {
//                // 数値として不適切な入力の場合のエラー表示
//                val intent = Intent(this, Error_Message::class.java)
//                intent.putExtra("ERROR_KEY", "数値として正しい形式で入力してください")
//                startActivity(intent)
//            }
//        }
//
//        //戻るボタンを押したときの処理
//        back_btn.setOnClickListener {
//            val intent = Intent(this, QR_Start_Inspection::class.java)
//            startActivity(intent)
//            finish()
//        }
    }
}