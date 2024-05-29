package jp.co.taika.kyoueihtapplication.function

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NyukoHeader {
    //クラスのインスタンスを作成
    private val token = Token()

    suspend fun getNyukoHeader(url: String, nyusyukko_num: Int): NyukoHeaderData? =
        withContext(Dispatchers.IO) {
            var tokenvalue = token.getToken()

            //Retrofitインスタンスを取得
            val nyukoHeaderDataService =
                RetrofitClient.retrofit.create(NyukoHeaderDataService::class.java)
            //request作成
            val requestData = NyukoHeaderData(
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
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            )
            //WebApiリクエストを実行
            val postCall = nyukoHeaderDataService.NyukoHeaderData(url, tokenvalue, requestData)

            try {
                //HTTPリクエストを送信し、レスポンスを受信
                val response = postCall.execute()
                //レスポンスのステータスコードを確認
                if (response.isSuccessful) {
                    return@withContext response.body()
                } else {
                    return@withContext null
                }

            } catch (e: Exception) {
                return@withContext null

            }
        }
}