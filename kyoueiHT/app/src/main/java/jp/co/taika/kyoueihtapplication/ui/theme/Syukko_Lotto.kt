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

class Syukko_Lotto : Import_Json() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.syukko_lotto)
        var lotto_number = findViewById<EditText>(R.id.lotto_number)
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)
        var syukko_Data = Syukko_Data_Save
        var syukko_number = findViewById<TextView>(R.id.syukko_number)
        var selectid_goods = findViewById<TextView>(R.id.selected_goods)
        selectid_goods.text = syukko_Data.selected_goods
        syukko_number.text = syukko_Data.plan_num.toString()

        val jsonData = intent.getStringExtra("t17_nskdh")
        var t17_nskdh = objectMapper.readValue(jsonData, T17_NSKDH_Master::class.java)

        next_btn.setOnClickListener {
            if(t17_nskdh.ロットFLAG == "2") {
                try {
                    syukko_Data.lotto = lotto_number.text.toString()
                    if(t17_nskdh.ロット番号 == syukko_Data.lotto) {
                        var data = t17_nskdh.copy()
                        val jsonData = objectMapper.writeValueAsString(data)
                        val intent = Intent(this, Syukko_Deadline::class.java)
                        intent.putExtra("t17_nskdh", jsonData)
                        startActivity(intent)
                        finish()
                    }else{
                        val intent = Intent(this, Error_Message::class.java)
                        val message = "ロットが異なります"
                        intent.putExtra("ERROR_KEY", message)
                        startActivity(intent)
                    }
                } catch (e: NumberFormatException) {
                    val intent = Intent(this, Error_Message::class.java)
                    val message = "必ず入力してください"
                    intent.putExtra("ERROR_KEY", message)
                    startActivity(intent)
                }
            }else{
                try {
                    syukko_Data.lotto = lotto_number.text.toString()
                    var data = t17_nskdh.copy()
                    val jsonData = objectMapper.writeValueAsString(data)
                    val intent = Intent(this, Syukko_Deadline::class.java)
                    intent.putExtra("t17_nskdh", jsonData)
                    startActivity(intent)
                    finish()
                } catch (e: NumberFormatException) {
                    var data = t17_nskdh.copy()
                    val jsonData = objectMapper.writeValueAsString(data)
                    val intent = Intent(this, Syukko_Deadline::class.java)
                    intent.putExtra("t17_nskdh", jsonData)
                    startActivity(intent)
                    finish()
                }
            }


        }

        back_btn.setOnClickListener {
            syukko_Data.lotto = ""
            var data = t17_nskdh.copy()
            val jsonData = objectMapper.writeValueAsString(data)
            val intent = Intent(this,Syukko_Location::class.java)
            intent.putExtra("t17_nskdh", jsonData)
            startActivity(intent)
            finish()
        }

    }
}
