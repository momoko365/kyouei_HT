package jp.co.taika.kyoueihtapplication.ui.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.zxing.integration.android.IntentIntegrator
import jp.co.taika.kyoueihtapplication.R
import jp.co.taika.kyoueihtapplication.function.QRDataList
import jp.co.taika.kyoueihtapplication.function.QRDatalistdAO
import jp.co.taika.kyoueihtapplication.function.ShohinDAO
import jp.co.taika.kyoueihtapplication.function.ShohinData_R
import jp.co.taika.kyoueihtapplication.function.UserDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.charset.Charset
//QRCodeMainからの遷移
class QR_Start_Inspection : ComponentActivity() {
    //データベース、daoのインスタンスを遅延初期化
    private lateinit var db: UserDataBase
    private lateinit var dao: ShohinDAO
    private lateinit var qrCodeListDao:QRDatalistdAO

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// レイアウトファイルをアクティビティにセットする
        setContentView(R.layout.qr_start_inspection)

        // データベースの初期化とビルドを非同期で行
        GlobalScope.launch {
            // データベースの初期化処理
            withContext(Dispatchers.IO) {
                db = Room.databaseBuilder(
                    applicationContext,
                    UserDataBase::class.java,
                    "taika.db"
                ).fallbackToDestructiveMigration().build()
                // WorkerDAOの初期化
                dao = db.shohindao()
                qrCodeListDao=db.qrdatalistdao()
            }
        }

        val btn = findViewById<Button>(R.id.QR)
        val backbtn = findViewById<Button>(R.id.back_btn)

        //ボタンを押した際QR読み取り画面に遷移する
        btn.setOnClickListener {
            IntentIntegrator(this).apply {
                captureActivity = QRCodeCaptureActivity_Inspection::class.java
            }.initiateScan()
        }
        backbtn.setOnClickListener {
            val intent = Intent(this, Start_Screen::class.java)
            startActivity(intent)
            finish()
        }


    }

    //inputとして与えられたバイト配列と、chunkSizesとして与えられた各チャンクのサイズが格納されたリストを受け取る
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
        // 残り全てを商品名として追加
        if (index < input.size) {
            chunks.add(input.copyOfRange(index, input.size))
        }
        //バイト配列を指定されたサイズのチャンクに分割し、それらのチャンクを含むリストを返す処理
        return chunks
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //読み取った結果の受け取り
        val result = IntentIntegrator.parseActivityResult(resultCode, data)
//        if (result.contents != null) {
//            //読み取った結果をトーストで表示する
//            Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()
//        }
        //読み取った結果を取得
        val qrCodeText = result.contents
        //QRコードから読み取ったテキストをShift-JISエンコーディング
        val charset = Charset.forName("SJIS")
        // qrCodeText を ShiftJIS に変換
        val qrCodeTextShiftJIS = qrCodeText.toByteArray(charset)

        lifecycleScope.launch(Dispatchers.IO) { // Activityのライフサイクルに基づいたスコープを使用
            if (qrCodeTextShiftJIS.isNotEmpty()) {
                val chunkSizes =
                    listOf(27, 8, 10, 8, 2, 3, 5, 1, 1, 1, 4, 1, 1, 1, 20, 35) // 固定サイズのチャンク
                val allChunks = splitByteArray(qrCodeTextShiftJIS, chunkSizes)
                val shohinCD = allChunks[0].toString(charset)
                val seizo_day = allChunks[1].toString(charset)
                val itijibuturyuCD = allChunks[2].toString(charset)
                val syomikigen = allChunks[3].toString(charset)
                val in_q = allChunks[4].toString(charset)
                val case_q = allChunks[5].toString(charset)
                val pallet_num = allChunks[6].toString(charset)
                val zaiko_pettern = allChunks[7].toString(charset)
                val tansu_flg = allChunks[8].toString(charset)
                val agari = allChunks[9].toString(charset)
                val yokomotisokoCD = allChunks[10].toString(charset)
                val okihura = allChunks[11].toString(charset)
                val pallet_category = allChunks[12].toString(charset)
                val kirikae_flg = allChunks[13].toString(charset)
                val ryakusyo = allChunks[14].toString(charset)
                val yobi = allChunks[15].toString(charset)

                //QRCodeListテーブルからShohinCDに対応するデータを取得
                val qrCodeData = qrCodeListDao.getShohinCD(shohinCD)
                //検品数量合計≧出荷数量の時
//                if (qrCodeData?.suryo!!.toInt() >= qrCodeData.zumi) {


                    if (qrCodeData != null) {
                        //QRCodeListテーブルからShohinの製造日に対応するデータを取得
                        val qrCodaData2 = qrCodeListDao.getShohiniroiro(seizo_day)
                        if (qrCodaData2 != null) {
                            val shohindataR = ShohinData_R(
                                shohinCD = shohinCD,
                                seizo_day = seizo_day,
                                itijibuturyuCD = itijibuturyuCD,
                                syomikigen = syomikigen,
                                in_q = in_q,
                                case_q = case_q,
                                pallet_num = pallet_num,
                                zaiko_pettern = zaiko_pettern,
                                tansu_flg = tansu_flg,
                                agari = agari,
                                yokomotisokoCD = yokomotisokoCD,
                                okihura = okihura,
                                pallet_category = pallet_category,
                                kirikae_flg = kirikae_flg,
                                ryakusyo = ryakusyo,
                                yobi = yobi,

                                )

                            dao.insert(shohindataR) // データを挿入

                            withContext(Dispatchers.Main) {
                                val intent =
                                    Intent(this@QR_Start_Inspection, Nyuko_Quantity_Int::class.java)
                                intent.putExtra("ShohinCD", shohinCD)
                                startActivity(intent)
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                val intent =
                                    Intent(this@QR_Start_Inspection, Error_Message::class.java)
                                val message = "製造日が異なります"
                                intent.putExtra("ERROR_KEY", message)
                                startActivity(intent)
                            }
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            val intent = Intent(this@QR_Start_Inspection, Error_Message::class.java)
                            val message = "検品外の商品です"
                            intent.putExtra("ERROR_KEY", message)
                            startActivity(intent)

                        }
                    }
//                } else {
//                    withContext(Dispatchers.Main) {
//                        val intent = Intent(this@QR_Start_Inspection, Error_Message::class.java)
//                        val message = "検品は完了しています"
//                        intent.putExtra("ERROR_KEY", message)
//                        startActivity(intent)
//                    }
//                }
            }
        }
    }
}