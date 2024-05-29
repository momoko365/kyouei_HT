package jp.co.taika.kyoueihtapplication.ui.theme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.room.Room
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.ViewfinderView
import jp.co.taika.kyoueihtapplication.R
import jp.co.taika.kyoueihtapplication.function.QRDatalistdAO
import jp.co.taika.kyoueihtapplication.function.UserDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QRCodeCaptureActivity_Inspection : CaptureActivity() {
    //データベース、daoのインスタンスを遅延初期化
    private lateinit var db: UserDataBase
    private lateinit var qrCodeListDao: QRDatalistdAO
    private lateinit var capture: CaptureManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_qrcode_inspection)

//データベースの初期化とビルドを非同期で行う
        GlobalScope.launch {
            // データベースの初期化処理
            withContext(Dispatchers.IO) {
                db = Room.databaseBuilder(
                    applicationContext,
                    UserDataBase::class.java,
                    "taika.db"
                ).fallbackToDestructiveMigration().build()
                qrCodeListDao=db.qrdatalistdao()
            }
        }

        var barcodeScannerView =
            findViewById<com.journeyapps.barcodescanner.CompoundBarcodeView>(R.id.qrcode_reader_inspection)
        // デフォルトで表示される赤い線を消す
        disableLaser(barcodeScannerView!!)

        capture = CaptureManager(this, barcodeScannerView!!)
        capture!!.initializeFromIntent(intent, savedInstanceState)
        capture!!.decode()
    }

    override fun onResume() {
        super.onResume()
        var barcodeView =
            findViewById<com.journeyapps.barcodescanner.CompoundBarcodeView>(R.id.qrcode_reader_inspection)
        barcodeView.resume()

        val back_btn =findViewById<Button>(R.id.modoru)
        back_btn.setOnClickListener {
            val intent = Intent(this, Start_Screen::class.java)
            startActivity(intent)
            finish()
        }
//出荷数量と検品数量が等しいかどうかテーブル全行チェックを行う
        var check_btn =findViewById<Button>(R.id.check)
        check_btn.setOnClickListener {
checkQuantities()
        }
    }

    private fun checkQuantities(){
        GlobalScope.launch(Dispatchers.IO) {
            val records =qrCodeListDao.getAll()
            var allMatch=true
            for(record in records){
                if (record.suryo.toInt() != record.zumi){
                    allMatch=false
                    break
                }
            }
            withContext(Dispatchers.Main){
                if(allMatch){//等しい時は画面５へ遷移
               val intent=Intent(this@QRCodeCaptureActivity_Inspection,QR_Kenpin_Finish::class.java)
                    startActivity(intent)

                }else{//異なる行がある時は画面６へ
                    val intent=Intent(this@QRCodeCaptureActivity_Inspection,QR_MikenpinItem::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        var barcodeView =
            findViewById<com.journeyapps.barcodescanner.CompoundBarcodeView>(R.id.qrcode_reader_inspection)
        barcodeView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        var barcodeView =
            findViewById<com.journeyapps.barcodescanner.CompoundBarcodeView>(R.id.qrcode_reader_inspection)
        barcodeView.pause()
    }

    // 赤い線を消す処理
    private fun disableLaser(decoratedBarcodeView: DecoratedBarcodeView) {
        val scannerAlphaField = ViewfinderView::class.java.getDeclaredField("SCANNER_ALPHA")
        scannerAlphaField.isAccessible = true
        scannerAlphaField.set(decoratedBarcodeView.viewFinder, intArrayOf(0))
    }
}