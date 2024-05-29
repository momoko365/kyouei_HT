package jp.co.taika.kyoueihtapplication.ui.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.room.Room
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureActivity
import jp.co.taika.kyoueihtapplication.R
import jp.co.taika.kyoueihtapplication.function.QRDataList
import jp.co.taika.kyoueihtapplication.function.QRDatalistdAO
import jp.co.taika.kyoueihtapplication.function.UserDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.charset.Charset
//Start_Screenからの遷移
class QRCodeMain : CaptureActivity() {
    //データベース、daoのインスタンスを遅延初期化
    private lateinit var db: UserDataBase
    private lateinit var dao: QRDatalistdAO

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // レイアウトファイルをアクティビティにセットする
        setContentView(R.layout.qrcodecaptureactivity)

        // データベースの初期化とビルドを非同期で行う
        GlobalScope.launch {
            // データベースの初期化処理
            withContext(Dispatchers.IO) {
                db = Room.databaseBuilder(
                    applicationContext,
                    UserDataBase::class.java,
                    "taika.db"
                ).fallbackToDestructiveMigration().build()

                // WorkerDAOの初期化
                dao = db.qrdatalistdao()
            }
        }
//それぞれ紐づけ
        var qrButton = findViewById<Button>(R.id.qr_button)
        var back_btn = findViewById<Button>(R.id.back_btn2)


        //QRカメラ起動ボタンを押した際QR読み取り画面に遷移する
        qrButton.setOnClickListener {
            IntentIntegrator(this).apply {
                captureActivity = QRCodeCaptureActivity::class.java
            }.initiateScan()
        }
        //戻るボタンを押した時の処理
        back_btn.setOnClickListener {
            val intent = Intent(this, Start_Screen::class.java)
            startActivity(intent)
            finish()
        }


    }

    // バイト配列をチャンクに分割するメソッド
    fun splitByteArray(input: ByteArray, chunkSizes: List<Int>): List<ByteArray> {
        //チャンクを格納するための空のリスト chunks を用意
        val chunks = mutableListOf<ByteArray>()
        //バイト配列の先頭から順に、各チャンクのサイズを取り出して、そのサイズごとにバイト配列を分割
        var index = 0
        //分割されたデータの塊を chunks リストに追加
        for (chunkSize in chunkSizes) {
            chunks.add(input.copyOfRange(index, kotlin.math.min(index + chunkSize, input.size)))
            index += chunkSize
        }
        //バイト配列を指定されたサイズのチャンクに分割し、それらのチャンクを含むリストを返す処理
        return chunks
    }

    // splitByteArrayメソッドで操作したShiftJISを操作
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //読み取った結果の受け取り
        val result = IntentIntegrator.parseActivityResult(resultCode, data)
        if (result.contents != null) {
            //読み取った結果をトーストで表示する
            Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()
        }
        if (result != null && result.contents != null) {
            //読み取った結果を取得
            val qrCodeText = result.contents
            //QRコードから読み取ったテキストをShift-JISエンコーディング
            val charset = Charset.forName("SJIS")
            // qrCodeText を ShiftJIS に変換
            val qrCodeTextShiftJIS = qrCodeText.toByteArray(charset)
            // ShiftJIS に変換した文字列の長さが 91 で割り切れるかどうかをチェック
            val isDivisibleBy91 = qrCodeTextShiftJIS.size % 91 == 0

            if (isDivisibleBy91) {
                // ShiftJIS に変換した文字列を91文字ごとに分割してリストに格納

                // qrCodeText の文字数が 91 で割り切れる場合の処理
                GlobalScope.launch {
                    // qrCodeTextShiftJISがnullでなく、要素を持っていることを確認
                    val datasetcount = qrCodeTextShiftJIS.size / 91
                    val qrDataLists = mutableListOf<QRDataList>()

                    // ここで最初にテーブルをクリアする
                    dao.deleteAll()
                    for (datasetindex in 0 until datasetcount) {
                        val startIndex = datasetindex * 91
                        val endIndex = startIndex + 91
                        val datasetBytes = qrCodeTextShiftJIS.copyOfRange(startIndex, endIndex)

                        if (qrCodeTextShiftJIS.isNotEmpty()) {
                            //文字列の分割
                            val chunkSizes =
                                listOf(27, 50,8,6)
                            val allChunks = splitByteArray(datasetBytes, chunkSizes)
// 分割されたデータからQRDataListエンティティを作成
//                            val qrDataLists = mutableListOf<QRDataList>()
                            // ここでQRDataListエンティティのリストを作成し、データベースに保存
                            for (i in allChunks.indices step chunkSizes.size) {
                                val shohinCD = allChunks[i + 0].toString(charset)
                                val item_name = allChunks[i + 1].toString(charset)
                                val seizo_day = allChunks[i + 2].toString(charset)
                                val suryo = allChunks[i + 3].toString(charset)
//                                val in_q = allChunks[i + 4].toString(charset)
//                                val case_q = allChunks[i + 5].toString(charset)
//                                val pallet_num = allChunks[i + 6].toString(charset)
//                                val zaiko_pettern = allChunks[i + 7].toString(charset)
//                                val tansu_flg = allChunks[i + 8].toString(charset)
//                                val agari = allChunks[i + 9].toString(charset)
//                                val yokomotisokoCD = allChunks[i + 10].toString(charset)
//                                val okihura = allChunks[i + 11].toString(charset)
//                                val pallet_category = allChunks[i + 12].toString(charset)
//                                val kirikae_flg = allChunks[i + 13].toString(charset)
//                                val ryakusyo = allChunks[i + 14].toString(charset)
//                                val yobi = allChunks[i + 15].toString(charset)

                                val qrDataList = QRDataList(
                                    shohinCD = shohinCD,
                                    item_name = item_name,
                                    seizo_day = seizo_day,
                                    suryo=suryo,
//                                    itijibuturyuCD = itijibuturyuCD,
//                                    syomikigen = syomikigen,
//                                    in_q = in_q,
//                                    case_q = case_q,
//                                    pallet_num = pallet_num,
//                                    zaiko_pettern = zaiko_pettern,
//                                    tansu_flg = tansu_flg,
//                                    agari = agari,
//                                    yokomotisokoCD = yokomotisokoCD,
//                                    okihura = okihura,
//                                    pallet_category = pallet_category,
//                                    kirikae_flg = kirikae_flg,
//                                    ryakusyo = ryakusyo,
//                                    yobi = yobi,
                                    zumi = 0
                                )

                                qrDataLists.add(qrDataList)
                            }
                            // データベースに非同期で挿入
//                            qrDataLists.forEach { qrDataList ->
//                                GlobalScope.launch(Dispatchers.IO) {

                                    // データベースに一括で挿入
                                    dao.insertAll(qrDataLists)
//                                }
                                //非同期処理が完了したらメインスレッドで画面遷移
                                withContext(Dispatchers.Main) {
                                    val intent =
                                        Intent(this@QRCodeMain, QR_Start_Inspection::class.java)
                                    startActivity(intent)

                                }

                            }
                        }

                }


            } else {
                // qrCodeText の文字数が 128 で割り切れない場合の処理
                val intent = Intent(this, Error_Message::class.java)
                val message = "QRコードが不正です"
                intent.putExtra("ERROR_KEY", message)
                startActivity(intent)

            }


        }
    }
}

