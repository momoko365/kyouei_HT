package jp.co.taika.kyoueihtapplication.ui.theme

import Syukko_Data_Save
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import jp.co.taika.kyoueihtapplication.R

class Syukko_Int_Num : Import_Json() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.syukko_int_num)
        var Int_Number = findViewById<EditText>(R.id.number)
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)
        var syukko_Data = Syukko_Data_Save
        var jsonData: String
        var writable_t17_nskd = nskd_read_json()

        next_btn.setOnClickListener {
            var data = t17_nskdh_master.copy(
                入出庫区分 = 2
            )
            var flaga = 0
            try {
                if (Int_Number.text.toString().toInt() <= 2147483647){
                    syukko_Data.plan_num = Int_Number.text.toString().toInt()
                }else{
                    syukko_Data.plan_num = 0
                }
            }catch(e: NumberFormatException){
                syukko_Data.plan_num = 0
            }
            var targetNumber = t17_nskh_list.T17_NSKH.find { it.入出庫番号 == syukko_Data.plan_num && it.伝票区分 == data.入出庫区分 }
            if(targetNumber != null){
                var suuryou1_sum = 0
                var suuryou2_sum = 0
                var suuryouzumi1_sum = 0
                var suuryouzumi2_sum = 0
                for (index in 0 until writable_t17_nskd.T17_NSKD.size) {
                    if(writable_t17_nskd.T17_NSKD[index].入出庫番号 == targetNumber.入出庫番号 && writable_t17_nskd.T17_NSKD[index].入出庫区分 == 2){
                        suuryou1_sum += writable_t17_nskd.T17_NSKD[index].入出荷数量１!!
                        suuryou2_sum += writable_t17_nskd.T17_NSKD[index].入出荷数量２!!
                        suuryouzumi1_sum += writable_t17_nskd.T17_NSKD[index].入出荷済数量１!!
                        suuryouzumi2_sum += writable_t17_nskd.T17_NSKD[index].入出荷済数量２!!
                    }
                }
                if(suuryou1_sum - suuryouzumi1_sum == 0 && suuryou2_sum - suuryouzumi2_sum == 0){
                    val intent = Intent(this, Error_Message::class.java)
                    val message = "全て入庫済みです"
                    intent.putExtra("ERROR_KEY",message)
                    startActivity(intent)
                }else{
                    var data2 = data.copy(
                        入出庫番号 = syukko_Data.plan_num,
                        得意先KEY = targetNumber.得意先KEY
                    )
                    jsonData = objectMapper.writeValueAsString(data2)
                    val intent = Intent(this, Syukko_Goods::class.java)
                    intent.putExtra("t17_nskdh", jsonData)
                    startActivity(intent)
                    finish()
                }
            }else{
                val intent = Intent(this, Error_Message::class.java)
                val message = "予定Noの誤りです"
                intent.putExtra("ERROR_KEY",message)
                startActivity(intent)
            }
        }

        back_btn.setOnClickListener {
            syukko_Data.plan_num = 0
            syukko_Data.selected_goods = ""
            syukko_Data.goods_barcode = ""
            syukko_Data.location = ""
            syukko_Data.lotto = ""
            syukko_Data.deadline_date = ""
            syukko_Data.quantity_1 = 0
            syukko_Data.quantity_2 = 0
            val intent = Intent(this,Start_Souko_Input::class.java)
            val flg = 0
            intent.putExtra("FLAG_KEY",flg)
            startActivity(intent)
            finish()
        }
    }
}
