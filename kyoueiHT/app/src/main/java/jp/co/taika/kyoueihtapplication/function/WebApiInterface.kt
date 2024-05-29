package jp.co.taika.kyoueihtapplication.function

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Url

object RetrofitClient {
    val retrofit = Retrofit.Builder()
        .baseUrl(Strings.BASE_URL) // start_screen.ktにてstringsコンストラクタにて設定
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}


//アクセストークンや認証トークンを管理するためのインターフェース。
// 一般的に、アクセストークンや認証トークンは、Webアプリケーションやモバイルアプリケーションなどの
// セキュアなリソースへのアクセスを制御するために使用されます
interface TokenSevice {
    @POST("gettoken")
    fun getToken(): Call<TokenResponse>

}

interface NyuSyukkoDataService {
    @POST
    fun NyukoData(
        @Url url: String,
        @Header("WebApi-Token") token: String,
        @Body requestData: NyukoData
    ): Call<ArrayList<NyukoData>>//戻り値をArrayListにする

}

interface WorkerNameDataService {
    @POST
    fun getWorker(
        @Url url: String,
        @Header("WebApi-Token") token: String,
        @Body requestData: WorkerData
    ): Call<WorkerData>
}

interface SoukoDataService {
    @POST
    fun SoukoData(
        @Url url: String,
        @Header("WebApi-Token") token: String,
        @Body requestData: SoukoData
    ): Call<SoukoData>
}

interface ShohinDataService {
    @POST
    fun ShohinData(
        @Url url: String,
        @Header("WebApi-Token") token: String,
        @Body requestData: ShohinData
    ): Call<ShohinData>
}

interface NyukoDetailDataService {
    @POST
    fun NyukoDetail(
        @Url url: String,
        @Header("WebApi-Token") token: String,
        @Body requestData: NyukoDetailData
    ): Call<NyukoDetailData>

    @PUT
    fun putNyukoDetail(
        @Url url: String,
        @Header("WebApi-Token") token: String,
        //@body requestData: <送信するデータのクラス名>
        @Body requestData: NyukoDetailData
        //): call<戻ってきたbodyを受け取るクラス名>
    ): Call<PutNyukoDetailData>
}

interface NyukoHeaderDataService {
    @POST
    fun NyukoHeaderData(
        @Url url: String,
        @Header("webApi-Token") token: String,
        @Body requestData: NyukoHeaderData
    ): Call<NyukoHeaderData>
}