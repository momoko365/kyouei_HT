package jp.co.taika.kyoueihtapplication.function

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Worker {
    //クラスのインスタンスを作成
    private val token = Token()

    suspend fun getWorkerName(url: String, id: Int): String = withContext(Dispatchers.IO) {
        var tokenvalue = token.getToken()

        //Retrofitのインスタンスを取得
        val workernamedataservice =
            RetrofitClient.retrofit.create(WorkerNameDataService::class.java)
        //requestを作成
        val requestData = WorkerData(id.toString(), "", "", "")
        //WebAPIリクエストを実行
        val postCall = workernamedataservice.getWorker(url, tokenvalue, requestData)
        try {
            val response = postCall.execute()
            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse?.id.isNullOrEmpty().not()) {
                    apiResponse?.id ?: ""
                } else {
                    apiResponse?.message ?: ""
                }
            } else {
                ""
            }
        } catch (e: Exception) {
            ""
        }
    }

}