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


class Syukko_Quantity_Int : Import_Json() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.syukko_quantity_int)
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)
        var quantity_1 = findViewById<EditText>(R.id.quantity_int)
        var number = findViewById<TextView>(R.id.number)
        var number2 = findViewById<TextView>(R.id.number2)
        var plan_num = findViewById<TextView>(R.id.plan_num)
        var quantity_2 = findViewById<EditText>(R.id._int)
        var syukko_Data = Syukko_Data_Save
        var writable_t17_nskd = nskd_read_json()
        var output_number = findViewById<TextView>(R.id.output_number)
        var selected_goods = findViewById<TextView>(R.id.selected_goods)
        output_number.text = syukko_Data.plan_num.toString()
        selected_goods.text = syukko_Data.selected_goods

        var jsonData = intent.getStringExtra("t17_nskdh")
        var t17_nskdh = objectMapper.readValue(jsonData, T17_NSKDH_Master::class.java)

        plan_num.text = t17_nskdh.入出荷数量１.toString() + "/" + t17_nskdh.入出荷数量２.toString()
        number.text = (t17_nskdh.入出荷数量１!! - t17_nskdh.入出荷済数量１!!).toString()
        number2.text = (t17_nskdh.入出荷数量２!! - t17_nskdh.入出荷済数量２!!).toString()

        next_btn.setOnClickListener {
            try {
                syukko_Data.quantity_1 = quantity_1.text.toString().toInt()
                syukko_Data.quantity_2 = quantity_2.text.toString().toInt()
                var targetNum =
                    writable_t17_nskd.T17_NSKD.find { it.商品KEY == t17_nskdh.商品KEY && it.入出庫区分 == t17_nskdh.入出庫区分 && it.賞味期限 == t17_nskdh.賞味期限 }
                if (targetNum != null) {
                    if ((t17_nskdh.入出荷数量１!! < syukko_Data.quantity_1 + targetNum.入出荷済数量１!!) || (t17_nskdh.入出荷数量２!! < syukko_Data.quantity_2 + targetNum.入出荷済数量２!!)) {
                        val intent = Intent(this, Error_Message::class.java)
                        val message = "予定数を超えています"
                        intent.putExtra("ERROR_KEY", message)
                        startActivity(intent)
                    } else if ((t17_nskdh.入出荷数量１!! - t17_nskdh.入出荷済数量１!! < syukko_Data.quantity_1) || (t17_nskdh.入出荷数量２!! - t17_nskdh.入出荷済数量２!! < syukko_Data.quantity_2)) {
                        val intent = Intent(this, Error_Message::class.java)
                        val message = "在庫が不足しています"
                        intent.putExtra("ERROR_KEY", message)
                        startActivity(intent)
                    } else {
                        t17_nskdh.入出荷済数量１ =
                            t17_nskdh.入出荷済数量１?.plus(syukko_Data.quantity_1)
                        t17_nskdh.入出荷済数量２ =
                            t17_nskdh.入出荷済数量２?.plus(syukko_Data.quantity_2)
                        var data = t17_nskdh.copy()
                        jsonData = objectMapper.writeValueAsString(data)
                        val intent = Intent(this, Syukko_Kakutei::class.java)
                        intent.putExtra("t17_nskdh", jsonData)
                        startActivity(intent)
                        finish()
                    }
                }

            } catch (e: NumberFormatException) {
                val intent = Intent(this, Error_Message::class.java)
                val message = "入力漏れです"
                intent.putExtra("ERROR_KEY", message)
                startActivity(intent)
            }
        }

        back_btn.setOnClickListener {
            syukko_Data.quantity_1 = 0
            syukko_Data.quantity_2 = 0
            var data = t17_nskdh.copy()
            jsonData = objectMapper.writeValueAsString(data)
            val intent = Intent(this, Syukko_Deadline::class.java)
            intent.putExtra("t17_nskdh", jsonData)
            startActivity(intent)
            finish()
        }
    }
}
