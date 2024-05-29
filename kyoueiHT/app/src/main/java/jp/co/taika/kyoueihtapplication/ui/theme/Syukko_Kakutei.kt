package jp.co.taika.kyoueihtapplication.ui.theme

import Syukko_Data_Save
import T17_NSKDH_Master
import T17_NSKD_List
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import jp.co.taika.kyoueihtapplication.R

class Syukko_Kakutei : Import_Json() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.syukko_kakutei)
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)
        var syukko_Data = Syukko_Data_Save
        var input_number = findViewById<TextView>(R.id.input_number)
        var goods = findViewById<TextView>(R.id.goods)
        var location_number = findViewById<TextView>(R.id.location_number)
        var lotto_number = findViewById<TextView>(R.id.lotto_number)
        var deadline = findViewById<TextView>(R.id.deadline)
        var number = findViewById<TextView>(R.id.number)

        lotto_number.text = syukko_Data.lotto
        location_number.text = syukko_Data.location
        input_number.text = syukko_Data.plan_num.toString()
        goods.text = syukko_Data.selected_goods
        deadline.text = syukko_Data.deadline_date
        number.text = syukko_Data.quantity_1.toString()+"/"+syukko_Data.quantity_2.toString()

        var jsonData = intent.getStringExtra("t17_nskdh")
        var t17_nskdh = objectMapper.readValue(jsonData, T17_NSKDH_Master::class.java)
        var writable_t17_nskd = nskd_read_json()

        next_btn.setOnClickListener {
            var data:T17_NSKD_List
            data = writable_t17_nskd.copy(
                T17_NSKD = writable_t17_nskd.T17_NSKD.map { entry ->
                    if (entry.入出庫番号 == t17_nskdh.入出庫番号 &&
                        entry.商品KEY == t17_nskdh.商品KEY &&
                        entry.入出庫区分 == t17_nskdh.入出庫区分 &&
                        entry.ロケーション == t17_nskdh.ロケーション &&
                        entry.賞味期限 == t17_nskdh.賞味期限) {
                        entry.copy(
                            入出荷済数量１ = t17_nskdh.入出荷済数量１,
                            入出荷済数量２ = t17_nskdh.入出荷済数量２
                        )
                    } else {
                        entry
                    }
                }
            )

            var fileName = "writable_nskd.json"
            var changeKey = "t17_NSKD"
            var changeKey2 = "T17_NSKD"
            write_json(data,fileName,changeKey,changeKey2)

            if ((t17_nskdh.入出荷数量１!! > t17_nskdh.入出荷済数量１!!)||(t17_nskdh.入出荷数量２!! > t17_nskdh.入出荷済数量２!!)){
                syukko_Data.location = ""
                syukko_Data.lotto = ""
                syukko_Data.deadline_date = ""
                syukko_Data.quantity_1 = 0
                syukko_Data.quantity_2 = 0
                var data2 = t17_nskdh.copy()
                jsonData = objectMapper.writeValueAsString(data2)
                val intent = Intent(this,Syukko_Location::class.java)
                intent.putExtra("t17_nskdh", jsonData)
                startActivity(intent)
                finish()
            }else if ((t17_nskdh.入出荷数量１!! == t17_nskdh.入出荷済数量１!!)&&(t17_nskdh.入出荷数量２!! == t17_nskdh.入出荷済数量２!!)){
                var sum = 0
                var sum1 = 0
                var sum2 = 0
                var sum3 = 0

                for (index in 0 until data.T17_NSKD.size) {
                    if (data.T17_NSKD[index].入出庫番号 == t17_nskdh.入出庫番号 && data.T17_NSKD[index].入出庫区分 == t17_nskdh.入出庫区分) {
                        sum += data.T17_NSKD[index].入出荷数量１!!
                        sum1 += data.T17_NSKD[index].入出荷数量２!!
                        sum2 += data.T17_NSKD[index].入出荷済数量１!!
                        sum3 += data.T17_NSKD[index].入出荷済数量２!!
                    }
                }
                if ((sum == sum2)&&(sum1 == sum3)){
                    syukko_Data.plan_num = 0
                    syukko_Data.selected_goods = ""
                    syukko_Data.goods_barcode = ""
                    syukko_Data.location = ""
                    syukko_Data.lotto = ""
                    syukko_Data.deadline_date = ""
                    syukko_Data.quantity_1 = 0
                    syukko_Data.quantity_2 = 0
                    val intent = Intent(this,Syukko_Int_Num::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    var t17_nskdh_reset = t17_nskdh_import()
                    var data = t17_nskdh_reset.copy(
                        入出庫番号 = t17_nskdh.入出庫番号,
                        得意先KEY = t17_nskdh.得意先KEY
                    )
                    syukko_Data.selected_goods = ""
                    syukko_Data.goods_barcode = ""
                    syukko_Data.location = ""
                    syukko_Data.lotto = ""
                    syukko_Data.deadline_date = ""
                    syukko_Data.quantity_1 = 0
                    syukko_Data.quantity_2 = 0
                    jsonData = objectMapper.writeValueAsString(data)
                    val intent = Intent(this,Syukko_Barcode::class.java)
                    intent.putExtra("t17_nskdh", jsonData)
                    startActivity(intent)
                    finish()
                }
            }
        }

        back_btn.setOnClickListener {
            var data = t17_nskdh.copy()
            jsonData = objectMapper.writeValueAsString(data)
            val intent = Intent(this,Syukko_Quantity_Int::class.java)
            intent.putExtra("t17_nskdh", jsonData)
            startActivity(intent)
            finish()
        }
    }
}
