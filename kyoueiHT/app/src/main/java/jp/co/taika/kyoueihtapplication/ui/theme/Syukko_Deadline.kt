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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Syukko_Deadline : Import_Json() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.syukko_deadline_in)
        var plan_deadline = findViewById<TextView>(R.id.plan_deadline)
        var deadline_year = findViewById<EditText>(R.id.deadline_year)
        var deadline_month = findViewById<EditText>(R.id.deadline_month)
        var deadline_day = findViewById<EditText>(R.id.deadline_day)
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)
        var syukko_Data = Syukko_Data_Save
        var writable_t17_nskd = nskd_read_json()
        var output_number = findViewById<TextView>(R.id.output_number)
        var selected_goods = findViewById<TextView>(R.id.selected_goods)
        output_number.text = syukko_Data.plan_num.toString()
        selected_goods.text = syukko_Data.selected_goods

        var jsonData = intent.getStringExtra("t17_nskdh")
        var t17_nskdh = objectMapper.readValue(jsonData, T17_NSKDH_Master::class.java)


        next_btn.setOnClickListener {
            var deadline_year_num:Int
            var deadline_month_num:Int
            var deadline_day_num:Int

            try {
                deadline_year_num = deadline_year.text.toString().toInt()
            }catch (e: NumberFormatException){
                deadline_year_num = 0
            }
            try {
                deadline_month_num = deadline_month.text.toString().toInt()
            }catch (e: NumberFormatException){
                deadline_month_num = 0
            }
            try {
                deadline_day_num = deadline_day.text.toString().toInt()
            }catch (e: NumberFormatException){
                deadline_day_num = 0
            }

            syukko_Data.deadline_date = deadline_year_num.toString() + "/" + deadline_month_num.toString() + "/" + deadline_day_num.toString()

            val inputFormatter = DateTimeFormatter.ofPattern("yyyy/M/d")
            val outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
            val parsedDate = LocalDate.parse(syukko_Data.deadline_date, inputFormatter)
            val parsed_date = parsedDate.format(outputFormatter)

            var data = t17_nskdh.copy()
            jsonData = objectMapper.writeValueAsString(data)
            if (parsed_date.toString() == "0000/00/00" && t17_nskdh.賞味期限 == "9999/12/31"){
                syukko_Data.deadline_date = "9999/12/31"
                var data2: T17_NSKDH_Master? = null
                var targetNumber = writable_t17_nskd.T17_NSKD.find { it.入出庫区分 == 1 && it.商品KEY == t17_nskdh.商品KEY && it.賞味期限 == t17_nskdh.賞味期限 && it.ロケーション == t17_nskdh.ロケーション }
                if(targetNumber != null){
                    data2 = data.copy(
                        在庫数１ = targetNumber.入出荷済数量１,
                        在庫数２ = targetNumber.入出荷済数量２
                    )
                    if(data2.在庫数１?.minus(data2.入出荷数量１!!)!! <= 0 || data2.在庫数２?.minus(data2.入出荷数量２!!)!! <= 0){
                        val intent = Intent(this, Error_Message::class.java)
                        val message = "在庫が不足しています"
                        intent.putExtra("ERROR_KEY",message)
                        startActivity(intent)
                    }else{
                        val intent = Intent(this, Syukko_Quantity_Int::class.java)
                        intent.putExtra("t17_nskdh", jsonData)
                        startActivity(intent)
                        finish()
                    }
                }
            }else if(parsed_date.toString() != t17_nskdh.賞味期限){
                val intent = Intent(this, Error_Message::class.java)
                val message = "日付の誤りです"
                intent.putExtra("ERROR_KEY",message)
                startActivity(intent)
            }else{
                var data2: T17_NSKDH_Master? = null
                var targetNumber = writable_t17_nskd.T17_NSKD.find { it.入出庫区分 == 1 && it.商品KEY == t17_nskdh.商品KEY && it.賞味期限 == t17_nskdh.賞味期限 && it.ロケーション == t17_nskdh.ロケーション }
                if(targetNumber != null){
                    data2 = data.copy(
                        在庫数１ = targetNumber.入出荷済数量１,
                        在庫数２ = targetNumber.入出荷済数量２
                    )
                    if(data2.在庫数１?.minus(data2.入出荷数量１!!)!! < 0 || data2.在庫数２?.minus(data2.入出荷数量２!!)!! < 0){
                        val intent = Intent(this, Error_Message::class.java)
                        val message = "在庫が不足しています"
                        intent.putExtra("ERROR_KEY",message)
                        startActivity(intent)
                    }else{
                        val intent = Intent(this, Syukko_Quantity_Int::class.java)
                        intent.putExtra("t17_nskdh", jsonData)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }

        back_btn.setOnClickListener {
            syukko_Data.deadline_date = ""
            var data = t17_nskdh.copy()
            jsonData = objectMapper.writeValueAsString(data)
            val intent = Intent(this,Syukko_Lotto::class.java)
            intent.putExtra("t17_nskdh", jsonData)
            startActivity(intent)
            finish()
        }

    }
}
