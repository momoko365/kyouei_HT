package jp.co.taika.kyoueihtapplication.ui.theme

import M09_HIN_List
import M22_SOUK_List
import M72_TNT_List
import T17_NSKDH_Master
import T17_NSKD_List
import T17_NSKH_List
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.google.gson.Gson
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.InputStreamReader


open class Import_Json : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    open lateinit var m72_tnt_list: M72_TNT_List
    open lateinit var m22_souk_list: M22_SOUK_List
    open lateinit var m09_hin_list: M09_HIN_List
    open lateinit var t17_nskh_list: T17_NSKH_List
    open lateinit var t17_nskd_list: T17_NSKD_List
    open lateinit var t17_nskdh_master: T17_NSKDH_Master

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        m72_tnt_list = m72_tnt_import()
        m22_souk_list = m22_souk_import()
        m09_hin_list = m09_hin_import()
        t17_nskh_list = t17_nskh_import()
        t17_nskd_list = t17_nskd_import()
        t17_nskdh_master = t17_nskdh_import()
        for (index in 0 until m09_hin_list.M09_HIN.size) {
            m09_hin_list.M09_HIN[index].商品KEY = (m09_hin_list.M09_HIN[index].商品コード + m09_hin_list.M09_HIN[index].得意先KEY.toString()).toInt()
        }
    }

    val objectMapper = ObjectMapper().registerModule(KotlinModule())

    open fun m72_tnt_import() : M72_TNT_List{
        val inputStream = applicationContext.resources.assets.open("M72_TNT.json")
        val jsonReader = InputStreamReader(inputStream, "UTF-8").readText()
        val m72_list = Gson().fromJson(jsonReader, M72_TNT_List::class.java)
        inputStream.close()
        return m72_list
    }

    open fun m22_souk_import() : M22_SOUK_List{
        val inputStream = applicationContext.resources.assets.open("M22_SOUK.json")
        val jsonReader = InputStreamReader(inputStream, "UTF-8").readText()
        val m22_list = Gson().fromJson(jsonReader, M22_SOUK_List::class.java)
        inputStream.close()
        return m22_list
    }

    open fun m09_hin_import() : M09_HIN_List{
        val inputStream = applicationContext.resources.assets.open("M09_HIN.json")
        val jsonReader = InputStreamReader(inputStream, "UTF-8").readText()
        val m09_list = Gson().fromJson(jsonReader, M09_HIN_List::class.java)
        inputStream.close()
        return m09_list
    }

    open fun t17_nskd_import() : T17_NSKD_List{
        val inputStream = applicationContext.resources.assets.open("T17_NSKD.json")
        val jsonReader = InputStreamReader(inputStream, "UTF-8").readText()
        val t17_nskd_list = Gson().fromJson(jsonReader, T17_NSKD_List::class.java)
        inputStream.close()
        return t17_nskd_list
    }

    open fun t17_nskh_import() : T17_NSKH_List{
        val inputStream = applicationContext.resources.assets.open("T17_NSKH.json")
        val jsonReader = InputStreamReader(inputStream, "UTF-8").readText()
        val t17_nskh_list = Gson().fromJson(jsonReader, T17_NSKH_List::class.java)
        inputStream.close()
        return t17_nskh_list
    }

    open fun t17_nskdh_import() : T17_NSKDH_Master{
        val inputStream = applicationContext.resources.assets.open("T17_NSKDH.json")
        val jsonReader = InputStreamReader(inputStream, "UTF-8").readText()
        val t17_nskdh_master = Gson().fromJson(jsonReader, T17_NSKDH_Master::class.java)
        inputStream.close()
        return t17_nskdh_master
    }

    open fun write_json(writeData: T17_NSKD_List,fileName: String,changeKey: String,changeKey2: String){
        var stringData = objectMapper.writeValueAsString(writeData)
        stringData = stringData.replace(changeKey, changeKey2)
        val writableFile = File(applicationContext.filesDir, fileName)
        writableFile.writer().use {
            it.write(stringData)
        }
    }

    open fun nskd_read_json(): T17_NSKD_List{
        //applicationContext.filesDirを使用して、
        // アプリケーションのファイルディレクトリ内にあるwritable_nskd.jsonへのパスを取得
        //取得したパスを使用してファイルを開くためのファイルオブジェクトを作成
        val inputFile = File(applicationContext.filesDir, "writable_nskd.json")
        //ファイルからデータを読み取るためのInputSteamを取得
        val inputStream: InputStream = FileInputStream(inputFile)
        //InputSteamReaderを使用してUTF-8エンコーディングでテキストデータを読み込む
        val stringData = InputStreamReader(inputStream, "UTF-8").readText()
        //Gsonを使用してJSON形式のテキストデータをwritable_t17_nskdに復元
        val writable_t17_nskd = Gson().fromJson(stringData, T17_NSKD_List::class.java)
        //ファイルを閉じる
        inputStream.close()
        //復元されたwritable_t17_nskdを返す
        return writable_t17_nskd
    }
}

