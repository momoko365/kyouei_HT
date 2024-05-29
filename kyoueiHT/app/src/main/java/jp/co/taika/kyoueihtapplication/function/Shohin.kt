package jp.co.taika.kyoueihtapplication.function

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Shohin {
    //クラスのインスタンスを作成
    private val token = Token()


    suspend fun getShohin(url: String, item_key: Int): ShohinData? = withContext(Dispatchers.IO) {
        var tokenvalue = token.getToken()


        //Retrofitインスタンスを取得
        val shohinDataService = RetrofitClient.retrofit.create(ShohinDataService::class.java)
        //request作成
        val requestData = ShohinData(item_key.toString(), "", "", "", "", "", "", "")
        //WebApaiリクエストを実行
        val postCall = shohinDataService.ShohinData(url, tokenvalue, requestData)

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
//
//
        } catch (e: Exception) {
            return@withContext null

        }
//    }


    }
}


