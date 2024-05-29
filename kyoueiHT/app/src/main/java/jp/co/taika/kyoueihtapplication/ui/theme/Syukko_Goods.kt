package jp.co.taika.kyoueihtapplication.ui.theme

import Syukko_Data_Save
import T17_NSKDH_Master
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import jp.co.taika.kyoueihtapplication.R

class Syukko_Goods : Import_Json() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.syukko_goods)
        var input_number = findViewById<TextView>(R.id.input_number)
        var zumi1 = findViewById<TextView>(R.id.zumi1)
        var zumi2 = findViewById<TextView>(R.id.zumi2)
        var plan1 = findViewById<TextView>(R.id.plan1)
        var plan2 = findViewById<TextView>(R.id.plan2)
        val goods_list: RadioGroup = findViewById(R.id.check_list)
        var next_btn = findViewById<Button>(R.id.next_btn)
        var back_btn = findViewById<Button>(R.id.back_btn)
        var syukko_Data = Syukko_Data_Save
        val jsonData = intent.getStringExtra("t17_nskdh")
        var t17_nskdh = objectMapper.readValue(jsonData, T17_NSKDH_Master::class.java)
        var writable_t17_nskd = nskd_read_json()
        var keep_date = arrayOf<String>()
        var keep_lank = arrayOf<Int>()
        var selected_date: String? = null

        input_number.text = t17_nskdh.入出庫番号.toString()
        for(index in 0 until writable_t17_nskd.T17_NSKD.size){
            if(writable_t17_nskd.T17_NSKD[index].入出庫番号 == t17_nskdh.入出庫番号 && writable_t17_nskd.T17_NSKD[index].入出庫区分 == t17_nskdh.入出庫区分){
                var targetNumber = m09_hin_list.M09_HIN.find { it.商品KEY == writable_t17_nskd.T17_NSKD[index].商品KEY}
                if(targetNumber != null) {
                    var goods = android.widget.RadioButton(this)
                    goods.text = targetNumber.商品名1
                    goods.setTextSize(TypedValue.COMPLEX_UNIT_PT, 18F)
                    goods_list.addView(goods)
                }
            }
        }


        goods_list.setOnCheckedChangeListener { group, checkedId ->
            val selected_goods_name = group.findViewById<RadioButton>(checkedId)
            for (index in 0 until goods_list.childCount) {
                val radioButton = goods_list.getChildAt(index) as? RadioButton
                if (radioButton != null && radioButton.text == selected_goods_name.text) {
                    val lank = group.indexOfChild(radioButton)
                    var keeplank = keep_lank.toMutableList()
                    keeplank.add(lank)
                    keep_lank = keeplank.toTypedArray()
                }
            }
            var lank = keep_lank.sorted()
            keep_lank = arrayOf()
            var targetNumber = m09_hin_list.M09_HIN.find { it.商品名1 == selected_goods_name.text }
            if(targetNumber != null){
                for(index in 0 until writable_t17_nskd.T17_NSKD.size){
                    if(writable_t17_nskd.T17_NSKD[index].商品KEY == targetNumber.商品KEY &&
                        writable_t17_nskd.T17_NSKD[index].入出庫番号 == t17_nskdh.入出庫番号 &&
                        writable_t17_nskd.T17_NSKD[index].入出庫区分 == t17_nskdh.入出庫区分){
                        var keepdate = keep_date.toMutableList()
                        keepdate.add(writable_t17_nskd.T17_NSKD[index].賞味期限.toString())
                        keep_date = keepdate.toTypedArray()
                    }
                }
            }
            var date = keep_date.sorted()
            keep_date = arrayOf()
            val goods_index = group.indexOfChild(selected_goods_name)
            for(index in 0 until lank.size){
                if(lank[index] == goods_index){
                    var targetNumber2 = writable_t17_nskd.T17_NSKD.find { it.賞味期限 == date[index] && it.入出庫区分 == t17_nskdh.入出庫区分 && it.入出庫番号 == t17_nskdh.入出庫番号 && it.商品KEY == targetNumber!!.商品KEY}
                    zumi1.text = targetNumber2!!.入出荷済数量１.toString()
                    zumi2.text = targetNumber2.入出荷済数量２.toString()
                    plan1.text = targetNumber2.入出荷数量１.toString()
                    plan2.text = targetNumber2.入出荷数量２.toString()
                    selected_date = date[index]
                }
            }


            /*var small_date: String
            val selected_goods_name = group.findViewById<RadioButton>(checkedId)
            if (selected_goods_name != null) {
                var targetNumber = m09_hin_list.M09_HIN.find{ it.商品名1 == selected_goods_name.text && it.得意先KEY == t17_nskdh.得意先KEY}
                if(targetNumber != null) {
                    for(index in 0 until writable_t17_nskd.T17_NSKD.size){
                        if(writable_t17_nskd.T17_NSKD[index].商品KEY == targetNumber.商品KEY && writable_t17_nskd.T17_NSKD[index].入出庫区分 == t17_nskdh.入出庫区分) {
                            var keepdate = keep.toMutableList()
                            keepdate.add(writable_t17_nskd.T17_NSKD[index].賞味期限.toString())
                            keep = keepdate.toTypedArray()
                        }
                    }
                    val dateFormat = SimpleDateFormat("yyyy/MM/dd")
                    try {
                        val smallDate: Date? = keep.map { dateFormat.parse(it) }.minOrNull()
                        if (smallDate != null) {
                            small_date = dateFormat.format(smallDate)
                            data = t17_nskdh.copy(
                                賞味期限 =  small_date
                            )
                            var targetNumber2 = writable_t17_nskd.T17_NSKD.find { it.商品KEY == targetNumber.商品KEY && it.賞味期限 == small_date}
                            if(targetNumber2 != null){
                                zumi1.text = targetNumber2.入出荷済数量１.toString()
                                zumi2.text = targetNumber2.入出荷済数量２.toString()
                                plan1.text = targetNumber2.入出荷数量１.toString()
                                plan2.text = targetNumber2.入出荷数量２.toString()
                            }
                        } else {

                        }
                    } catch (e: Exception) {

                    }
                }
            }
            keep = arrayOf()*/
        }

        next_btn.setOnClickListener {
            val id = goods_list.checkedRadioButtonId
            val selected_id = goods_list.findViewById<RadioButton>(id)
            try{
                syukko_Data.selected_goods = selected_id.text.toString()
            }catch (e: NullPointerException){
                syukko_Data.selected_goods = null.toString()
            }
            if(syukko_Data.selected_goods != "null"){
                var targetNumber = m09_hin_list.M09_HIN.find{ it.商品名1 == syukko_Data.selected_goods}
                var data2: T17_NSKDH_Master
                if (targetNumber != null){
                    var targetNumber2 = writable_t17_nskd.T17_NSKD.find { it.商品KEY == targetNumber.商品KEY && it.入出庫区分 == t17_nskdh.入出庫区分 && it.賞味期限 == selected_date}
                    if(targetNumber2 != null) {
                        data2 = t17_nskdh.copy(商品KEY = targetNumber.商品KEY,
                                            JANコード = targetNumber.JANコード,
                                            ロケーションFLAG = targetNumber.ロケーションFLAG,
                                            ロットFLAG = targetNumber.ロットFLAG,
                                            賞味期限 = targetNumber2.賞味期限,)
                        val jsonData = objectMapper.writeValueAsString(data2)
                        val intent = Intent(this, Syukko_Barcode::class.java)
                        intent.putExtra("t17_nskdh", jsonData)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }

        back_btn.setOnClickListener {
            syukko_Data.selected_goods = ""
            val intent = Intent(this,Syukko_Int_Num::class.java)
            startActivity(intent)
            finish()
        }
    }
}
