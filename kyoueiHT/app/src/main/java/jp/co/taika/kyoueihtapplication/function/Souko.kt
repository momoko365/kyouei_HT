package jp.co.taika.kyoueihtapplication.function

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Souko {
    //クラスのインスタンスを作成
    private val token = Token()


    suspend fun getSouko(url: String, key: Int): String = withContext(Dispatchers.IO) {
        var tokenvalue = token.getToken()

        //Rettofitのインスタンスを取得
        val soukoDataService = RetrofitClient.retrofit.create(SoukoDataService::class.java)
        //request作成
        val requestData = SoukoData(key.toString(), "", "", "")
        //WebApiリクエストを実行
        val postCall = soukoDataService.SoukoData(url, tokenvalue, requestData)
        try {
            val response = postCall.execute()
            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse?.key.isNullOrEmpty().not()) {
                    apiResponse?.key ?: ""
                } else {
//                    apiResponse?:""
                    ""
                }
            } else {
                ""
            }

        } catch (e: Exception) {
            ""
        }
    }
}