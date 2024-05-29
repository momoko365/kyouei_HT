package jp.co.taika.kyoueihtapplication.function

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PutNyukoDetail {
    //クラスのインスタンスを作成
    private val token = Token()


    suspend fun getPutNyukoDetail(
        url: String,
        nyusyukko_num: Int,
        item_key: Int,
        lotto_num: Int,
        deadline_date: String?,
        nyusyukka_quantity1: Int,
        nyusyukka_quantity2: Int
    ): PutNyukoDetailData? =
        withContext(Dispatchers.IO) {
            var tokenvalue = token.getToken()

            // Retrofitのインスタンスを取得
            val nyukoDetailDataService =
                RetrofitClient.retrofit.create(NyukoDetailDataService::class.java)
            // リクエスト作成
            val requestData =
                NyukoDetailData(
                    nyusyukko_num.toString(),
                    "",
                    "",
                    item_key.toString(),
                    "",
                    lotto_num.toString(),
                    nyusyukka_quantity1.toString(),
                    nyusyukka_quantity2.toString(),
                    "",
                    "",
                    deadline_date.toString(),
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                )
            //WebApiリクエストを実行
            val postCall = nyukoDetailDataService.putNyukoDetail(url, tokenvalue, requestData)


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