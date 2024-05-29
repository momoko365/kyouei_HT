package jp.co.taika.kyoueihtapplication.function

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Token {
    // トークンの取得
    suspend fun getToken(): String = withContext(Dispatchers.IO) {
        // Retrofitのインスタンスを取得
        val tokenService = RetrofitClient.retrofit.create(TokenSevice::class.java)
        // WebAPIリクエストを実行
        val tokenCall = tokenService.getToken()
        try {
            val response = tokenCall.execute()
            if (response.isSuccessful) {
                val tokenResponse = response.body()
                tokenResponse?.token ?: ""
            } else {
                ""
            }
        } catch (e: Exception) {
            ""
        }
    }
}

