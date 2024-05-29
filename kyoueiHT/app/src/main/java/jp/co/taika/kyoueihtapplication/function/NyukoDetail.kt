package jp.co.taika.kyoueihtapplication.function

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NyukoDetail {
    //クラスのインスタンスを作成
    private val token = Token()

    suspend fun getNyukoDetail(url: String, nyusyukko_num: Int): NyukoDetailData? =
        withContext(Dispatchers.IO) {
            var tokenvalue = token.getToken()

            //Retrofitのインスタンスを取得
            val nyukoDetailDataService =
                RetrofitClient.retrofit.create(NyukoDetailDataService::class.java)
            //request作成
            val requestData =
                NyukoDetailData(
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
                    ""
                )
            //WebApiリクエストを実行
            val postCall = nyukoDetailDataService.NyukoDetail(url, tokenvalue, requestData)


            try {
                //HTTPリクエストを送信し、レスポンスを受信
                val response = postCall.execute()
                //レスポンスのステータスコードを確認
                if (response.isSuccessful) {
                    //レスポンスボディを取得
                    return@withContext response.body()
                } else {
                    //レスポンスが成功以外の場合のエラーを処理
                    return@withContext null
                }

            } catch (e: Exception) {
                return@withContext null
            }


        }
}