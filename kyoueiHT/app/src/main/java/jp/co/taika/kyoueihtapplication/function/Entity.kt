package jp.co.taika.kyoueihtapplication.function

data class TokenResponse(
    val token: String,
    val rc: String,
    val message: String
)

data class NyukoData(
    val nyusyukko_num: String,
    val gyo_num: String,
    val syori_category: String,
    val nyusyukko_category: String,
    val item_key: String,
    val soko_key: String,
    val location: String,
    val lotto_num: String,
    val nyuko_date: String,
    val nyusyukka_quantity1: String,
    val nyusyukka_quantity2: String,
    val nyusyukkazumi_quantity1: String,
    val nyusyukkazumi_quantity2: String,
    val tekiyo: String,
    val item_pick: String,
    val otodoke_pick: String,
    val soko_pick: String,
    val hokanryokeisanstart_date: String,
    val deadline: String,
    val kakutei_category: String,
    val tenso_category: String,
    val torokutanto_name: String,
    val toroku_date: String,
    val kosintanto_name: String,
    val kosin_date: String,
    val delete_date: String


)

data class WorkerData(
    val id: String,
    val name: String,
    val delete_at: String,
    val message: String
)

data class SoukoData(
    val key: String,
    val soko_id: String,
    val soko_name: String,
    val delete_at: String,

    )

data class ShohinData(
    val item_key: String,
    val tokui_key: String,
    val item_id: String,
    val item_name: String,
    val jan: String,
    val delete_at: String,
    val location_flg: String,
    val lot_flg: String
)

data class NyukoDetailData(
    val nyusyukko_num: String,
    val gyo_num: String,
    val tokui_key: String,
    val item_key: String,
    val location: String,
    val lotto_num: String,
    val nyusyukka_quantity1: String,
    val nyusyukka_quantity2: String,
    var nyusyukkazumi_quantity1: String,
    var nyusyukkazumi_quantity2: String,
    val deadline_date: String,
    val jan: String,
    val location_flg: String,
    val lot_flg: String,
    val nyusyukko_category: String,
    val zaiko_1: String,
    val zaiko_2: String

)

data class PutNyukoDetailData(
    val rc: String
)


data class NyukoHeaderData(
    val nyusyukko_num: String,
    val denpyo_category: String,
    val batti_num: String,
    val ninusidenpyo_num: String,
    val nyusyukko_date: String,
    val tokui_key: String,
    val jikansitei_category: String,
    val jikansitei: String,
    val gentyaku_category: String,
    val biko: String,
    val syuyaku_date: String,
    val nohin_date: String,
    val ninusi_key: String,
    val syukkasaki_key: String,
    val syukkasaki_postcode: String,
    val syukkasaki_name1: String,
    val syukkasaki_name2: String,
    val syukkasaki_address1: String,
    val syukkasaki_address2: String,
    val syukkasaki_tel_num: String,
    val course_key: String,
    val yusobin_key: String,
    val nohinsyo_hakko_category: String,
    val okurijo_hakko_category: String,
    val nihuda_hakko_maisu: String,
    val kokutisu: String,
    val weight: String,
    val nyuryoku_category: String,
    val torokutanto_name: String,
    val toroku_date: String,
    val kosintanto_name: String,
    val kosin_date: String,
    val delete_date: String

)