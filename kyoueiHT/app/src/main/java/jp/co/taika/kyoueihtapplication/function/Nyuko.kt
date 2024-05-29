package jp.co.taika.kyoueihtapplication.function

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Nyuko {
    //クラスのインスタンスを作成
    private val token = Token()

    suspend fun getNyuSyukko(url: String, nyusyukko_num: Int): ArrayList<NyukoData>? =
        withContext(Dispatchers.IO) {
            var tokenvalue = token.getToken()

            // Retrofitのインスタンスを取得
            val nyuSyukkoDataService =
                RetrofitClient.retrofit.create(NyuSyukkoDataService::class.java)
            //request作成
            val requestData = NyukoData(
                nyusyukko_num.toString(),
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            )
            // WebAPIリクエストを実行
            val postCall = nyuSyukkoDataService.NyukoData(url, tokenvalue, requestData)
            try {
                //HTTPリクエストを送信しレスポンスを受信
                val response = postCall.execute()
                //レスポンスのステータスコードを確認
                if (response.isSuccessful) {
                    //レスポンスボディの取得
                    val apiResponse: ArrayList<NyukoData>? =
                        response.body() as? ArrayList<NyukoData>
                    return@withContext apiResponse
                } else {
                    return@withContext null
                }


            } catch (e: Exception) {
                return@withContext null

            }
        }
}