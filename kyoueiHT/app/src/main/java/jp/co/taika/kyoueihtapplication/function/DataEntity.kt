package jp.co.taika.kyoueihtapplication.function

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//最初の入出荷予定のQR(91バイト)
@Entity(tableName = "qrdatalist")
data class QRDataList(
    @PrimaryKey
    val shohinCD:String,
    val item_name: String,
    val seizo_day:String,
    val suryo:String,
    var zumi:Int

)

//パレットQR（128バイト )
@Entity
data class ShohinData_R(
    @PrimaryKey
    val shohinCD:String,
    val seizo_day:String,
    val itijibuturyuCD:String,
    val syomikigen:String,
    val in_q:String,
    val case_q:String,
    val pallet_num:String,
    val zaiko_pettern:String,
    val tansu_flg:String,
    val agari:String,
    val yokomotisokoCD:String,
    val okihura:String,
    val pallet_category:String,
    val kirikae_flg:String,
    val ryakusyo:String,
    val yobi:String,

)
@Entity
data class TokenResponse_R constructor(
    @PrimaryKey
    val token: String,
    val rc: String,
    val message: String
)

@Entity
data class NyukoData_R(
    @PrimaryKey(autoGenerate = true)
    val nyusyukko_num:Int,
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

@Entity
data class WorkerData_R(
    @PrimaryKey(autoGenerate = true)

    var id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name="delete_at")
    val delete_at: String

)

@Entity
data class SoukoData_R(
    @PrimaryKey(autoGenerate = true)
    var key: Int,
    @ColumnInfo(name = "id")
    val soko_id: String,
    @ColumnInfo(name = "name")
    val soko_name: String,
    @ColumnInfo(name = "delete_at")
    val delete_at: String,

    )



@Entity
data class NyukoDetailData_R(
    @PrimaryKey(autoGenerate = true)
    val nyusyukko_num: Int,
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

@Entity
data class PutNyukoDetailData_R constructor(
    @PrimaryKey
    val rc: String
)

@Entity
data class NyukoHeaderData_R(
    @PrimaryKey(autoGenerate = true)
    val nyusyukko_num: Int,
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

