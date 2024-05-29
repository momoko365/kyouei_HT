package jp.co.taika.kyoueihtapplication.function

object Strings {
    var BASE_URL: String = ""
    var NYUSYUKKO_URL: String = ""
    var WORKER_URL: String = ""
    var SOUKO_URL: String = ""
    var SHOHIN_URL: String = ""
    var NYUKODETAIL_URL: String = ""
    var NYUKOHEADER_URL: String = ""
    var PUTNYUKODETAIL_URL: String = ""

    fun init(
        baseUrl: String,
        nyusyukkoUrl: String,
        workerUrl: String,
        soukoUrl: String,
        shohinUrl: String,
        nyukoDetailUrl: String,
        nyukoHeaderUrl: String,
        putnyukodetailUrl: String
    ) {
        BASE_URL = baseUrl
        NYUSYUKKO_URL = nyusyukkoUrl
        WORKER_URL = workerUrl
        SOUKO_URL = soukoUrl
        SHOHIN_URL = shohinUrl
        NYUKODETAIL_URL = nyukoDetailUrl
        NYUKOHEADER_URL = nyukoHeaderUrl
        PUTNYUKODETAIL_URL = putnyukodetailUrl

    }
}