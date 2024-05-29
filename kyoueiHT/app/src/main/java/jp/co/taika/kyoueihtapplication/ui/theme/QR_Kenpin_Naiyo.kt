package jp.co.taika.kyoueihtapplication.ui.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import jp.co.taika.kyoueihtapplication.R
import jp.co.taika.kyoueihtapplication.function.QRDatalistdAO
import jp.co.taika.kyoueihtapplication.function.ShohinDAO
import jp.co.taika.kyoueihtapplication.function.UserDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class QR_Kenpin_Naiyo : ComponentActivity() {
    private lateinit var db: UserDataBase
    private var qrCodeListDao: QRDatalistdAO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qr_kenpin_naiyo)

        lifecycleScope.launch {
            // データベースの初期化
            withContext(Dispatchers.IO) {
                db = Room.databaseBuilder(
                    applicationContext,
                    UserDataBase::class.java,
                    "taika.db"
                ).fallbackToDestructiveMigration().build()

                // DAOの初期化
                qrCodeListDao = db.qrdatalistdao()
            }
            qrCodeListDao?.let { dao ->
                val records = withContext(Dispatchers.IO) { dao.getAll() }
                withContext(Dispatchers.Main) {
                    val container = findViewById<LinearLayout>(R.id.qr_kenpin)
                    container.removeAllViews()  // 以前のビューをクリア
                    records.forEach { record ->
                        // 商品名の新しい TextView を生成
                        val nameTextView = TextView(this@QR_Kenpin_Naiyo).apply {
                            text = "商品名: ${record.item_name}"
                            textSize = 16f
                            layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            ).apply {
                                setMargins(20, 10, 20, 10) // マージンを設定
                            }
                        }
                        container.addView(nameTextView)

                        // 製造日の新しい TextView を生成
                        val dateTextView = TextView(this@QR_Kenpin_Naiyo).apply {
                            text = "製造日: ${record.seizo_day}"
                            textSize = 16f
                            layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            ).apply {
                                setMargins(20, 10, 20, 10) // マージンを設定
                            }
                        }
                        container.addView(dateTextView)

                        // 数量の新しい TextView を生成
                        val quantityTextView = TextView(this@QR_Kenpin_Naiyo).apply {
                            text = "数量: ${record.zumi}"
                            textSize = 16f
                            setStyleBasedOnQuantity(record.zumi, record.suryo.toInt())

                        }
                        container.addView(quantityTextView)
                    }
                }
            }
        }
        val back_btn = findViewById<Button>(R.id.back_btn)
        back_btn.setOnClickListener {
            val intent = Intent(this, QR_MikenpinItem::class.java)
            startActivity(intent)
        }
    }

    private fun TextView.setStyleBasedOnQuantity(zumi: Int, suryo: Int) {
        if (zumi != suryo) {
            this.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
        }
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(20, 10, 20, 10) // マージンを設定
        }
    }
}


//            qrCodeListDao?.let { dao ->
//                val records = withContext(Dispatchers.IO) { dao.getAll() }
//                withContext(Dispatchers.Main) {
//                    val container = findViewById<LinearLayout>(R.id.qr_kenpin)
//                    records.forEach { record ->
//                        // 商品名
//                        val nameTextView = findViewById<TextView>(R.id.name).apply {
//                            text = "商品名: ${record.item_name}"
//                            textSize = 16f
//                            layoutParams = LinearLayout.LayoutParams(
//                                LinearLayout.LayoutParams.MATCH_PARENT,
//                                LinearLayout.LayoutParams.WRAP_CONTENT
//                            )
//                            setPadding(20, 10, 20, 10)
//                        }
//                        container.addView(nameTextView)
//
//                        // 製造日
//                        val dateTextView =findViewById<TextView>(R.id.date).apply {
//                            text = "製造日: ${record.seizo_day}"
//                            textSize = 16f
//                            layoutParams = LinearLayout.LayoutParams(
//                                LinearLayout.LayoutParams.MATCH_PARENT,
//                                LinearLayout.LayoutParams.WRAP_CONTENT
//                            )
//                            setPadding(20, 10, 20, 10)
//                        }
//                        container.addView(dateTextView)
//
//                        // 数量
//                        val quantityTextView = findViewById<TextView>(R.id.zumi).apply {
//                            text = "数量: ${record.zumi}"
//                            textSize = 16f
//                            layoutParams = LinearLayout.LayoutParams(
//                                LinearLayout.LayoutParams.MATCH_PARENT,
//                                LinearLayout.LayoutParams.WRAP_CONTENT
//                            )
//                            setPadding(20, 10, 20, 10)
//                        }
//                        container.addView(quantityTextView)
//                    }
//                }
//            }
//        }
//    }
//}


//            // データの取得とUIの更新
//            qrCodeListDao?.let { dao ->
//                val records = withContext(Dispatchers.IO) { dao.getAll() }
//                withContext(Dispatchers.Main) {
//                    val container = findViewById<LinearLayout>(R.id.qr_kenpin)
//                    records.forEach { record ->
//                        var name=findViewById<TextView>(R.id.name)
//                        var date =findViewById<TextView>(R.id.date)
//                        var zumi=findViewById<TextView>(R.id.zumi)
//                        name.text=record.item_name
//                        date.text=record.seizo_day
//                        zumi.text=record.zumi.toString()
////                        val nameTextView = TextView(this@QR_Kenpin_Naiyo).apply {
////                            text = "商品名: ${record.item_name}, 製造日: ${record.seizo_day}, 数量: ${record.zumi}"
////                            textSize = 16f
////                            layoutParams = LinearLayout.LayoutParams(
////                                LinearLayout.LayoutParams.MATCH_PARENT,
////                                LinearLayout.LayoutParams.WRAP_CONTENT
////                            )
////                            setPadding(20, 10, 20, 10)
//                        }
////                        container.addView(nameTextView)
//
//                }
//            }
//        }
//    }
//}