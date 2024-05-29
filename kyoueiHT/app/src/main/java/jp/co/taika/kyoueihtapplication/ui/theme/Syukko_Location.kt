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

class Syukko_Location : Import_Json() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.syukko_location)
        var location_number = findViewById<EditText>(R.id.location_number)
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)
        var syukko_Data = Syukko_Data_Save
        var syukko_number = findViewById<TextView>(R.id.syukko_number)

        var selectid_goods = findViewById<TextView>(R.id.selected_goods)
        var jsonData = intent.getStringExtra("t17_nskdh")
        var t17_nskdh = objectMapper.readValue(jsonData, T17_NSKDH_Master::class.java)
        var writable_t17_nskd = nskd_read_json()

        selectid_goods.text = syukko_Data.selected_goods
        syukko_number.text = syukko_Data.plan_num.toString()


        next_btn.setOnClickListener {
            if (t17_nskdh.ロケーションFLAG == "2") {
                try {
                    syukko_Data.location = location_number.text.toString()
                    var data: T17_NSKDH_Master? = null
                    var targetNumber = writable_t17_nskd.T17_NSKD.find { it.ロケーション == syukko_Data.location && it.入出庫区分 == t17_nskdh.入出庫区分 && it.入出庫番号 == t17_nskdh.入出庫番号 && it.賞味期限 == t17_nskdh.賞味期限 && it.商品KEY == t17_nskdh.商品KEY}
                    if(targetNumber != null) {
                        data = t17_nskdh.copy(
                            ロケーション = targetNumber.ロケーション,
                            ロット番号 = targetNumber.ロット番号,
                            入出荷数量１ = targetNumber.入出荷数量１,
                            入出荷数量２ = targetNumber.入出荷数量２,
                            入出荷済数量１ = targetNumber.入出荷済数量１,
                            入出荷済数量２ = targetNumber.入出荷済数量２
                            )
                        val jsonData = objectMapper.writeValueAsString(data)
                        val intent = Intent(this, Syukko_Lotto::class.java)
                        intent.putExtra("t17_nskdh", jsonData)
                        startActivity(intent)
                        finish()
                    }else{

                    }
                } catch (e: NumberFormatException) {
                    val intent = Intent(this, Error_Message::class.java)
                    val message = "必ず入力してください"
                    intent.putExtra("ERROR_KEY", message)
                    startActivity(intent)
                }
            } else {
                try {
                    syukko_Data.location = location_number.text.toString()
                    var data = t17_nskdh.copy()
                    val jsonData = objectMapper.writeValueAsString(data)
                    val intent = Intent(this, Syukko_Lotto::class.java)
                    intent.putExtra("t17_nskdh", jsonData)
                    startActivity(intent)
                    finish()
                } catch (e: NumberFormatException) {
                    var data = t17_nskdh.copy()
                    val jsonData = objectMapper.writeValueAsString(data)
                    val intent = Intent(this, Syukko_Lotto::class.java)
                    intent.putExtra("t17_nskdh", jsonData)
                    startActivity(intent)
                    finish()
                }

            }

        }
        back_btn.setOnClickListener {
            syukko_Data.location = ""
            var data = t17_nskdh.copy()
            val jsonData = objectMapper.writeValueAsString(data)
            val intent = Intent(this, Syukko_Barcode::class.java)
            intent.putExtra("t17_nskdh", jsonData)
            startActivity(intent)
            finish()
        }
    }
}
