package jp.co.taika.kyoueihtapplication.ui.theme

import Syukko_Data_Save
import T17_NSKDH_Master
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import jp.co.taika.kyoueihtapplication.R

class Syukko_Barcode : Import_Json() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.syukko_barcode)
        var puroduct_b = findViewById<EditText>(R.id.puroduct_b)
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)
        var syukko_Data = Syukko_Data_Save
        var output_number = findViewById<TextView>(R.id.output_number)
        var selected_goods = findViewById<TextView>(R.id.selected_goods)
        val jsonData = intent.getStringExtra("t17_nskdh")
        var t17_nskdh = objectMapper.readValue(jsonData, T17_NSKDH_Master::class.java)
        var writable_t17_nskd = nskd_read_json()
        var keep = arrayOf<String>()
        output_number.text = syukko_Data.plan_num.toString()
        selected_goods.text = syukko_Data.selected_goods.toString()


        next_btn.setOnClickListener {
            try {
                syukko_Data.goods_barcode = puroduct_b.text.toString()
                if(syukko_Data.goods_barcode == t17_nskdh.JANコード){
                    var data = t17_nskdh
                    val jsonData = objectMapper.writeValueAsString(data)
                    val intent = Intent(this,Syukko_Location::class.java)
                    intent.putExtra("t17_nskdh", jsonData)
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this, Error_Message::class.java)
                    val message = "商品が異なります"
                    intent.putExtra("ERROR_KEY",message)
                    startActivity(intent)
                }
            }catch(e: NumberFormatException){
                val intent = Intent(this, Error_Message::class.java)
                val message = "入力漏れです"
                intent.putExtra("ERROR_KEY",message)
                startActivity(intent)
            }


        }

        back_btn.setOnClickListener {
            syukko_Data.goods_barcode = ""
            var t17_nskdh_reset = t17_nskdh_import()
            var data = t17_nskdh_reset.copy(
                入出庫番号 = t17_nskdh.入出庫番号,
                得意先KEY = t17_nskdh.得意先KEY,
                入出庫区分 = t17_nskdh.入出庫区分
            )
            val jsonData = objectMapper.writeValueAsString(data)
            val intent = Intent(this,Syukko_Goods::class.java)
            intent.putExtra("t17_nskdh", jsonData)
            startActivity(intent)
            finish()
        }
    }
}
