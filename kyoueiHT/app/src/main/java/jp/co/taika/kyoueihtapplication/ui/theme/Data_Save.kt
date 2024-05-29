import androidx.activity.ComponentActivity

object Start_Data_Save : ComponentActivity(){
    var work_year = 0
    var work_month = 0
    var work_day = 0
    var worker_id = 0
    var souko_code = 0
    var inventory_year = 0
    var inventory_month = 0
    var inventory_day = 0
    var nyusyukko_judge = 0
}
object Nyuko_Data_Save : ComponentActivity(){
    var plan_num = 0
    var selected_goods = ""
    var goods_barcode = ""
    var location = ""
    var lotto = ""
    var deadline_date = ""
    var quantity_1 = 0
    var quantity_2 = 0

}
object Syukko_Data_Save : ComponentActivity(){
    var plan_num = 0
    var selected_goods = ""
    var goods_barcode = ""
    var location = ""
    var lotto = ""
    var deadline_date = ""
    var quantity_1 = 0
    var quantity_2 = 0

}

data class M72_TNT_Master(
    var 担当者コード: Int?,
    var 担当者名: String?,
    var 削除日付: String?
)


data class M22_SOUK_Master(
    var 保管倉庫KEY: Int?,
    var 保管倉庫コード: String?,
    var 保管倉庫名: String?,
    var 削除日付: String?
)


data class M09_HIN_Master(
    var 商品KEY: Int?,
    var 得意先KEY: Int?,
    var 商品コード: String?,
    var 商品名1: String?,
    var JANコード: String?,
    var 削除日付: String?,
    var ロケーションFLAG: String?,
    var ロットFLAG: String?
)


data class T17_NSKD_Master(
    var 入出庫番号: Int?,
    var 行番号: Int?,
    var 処理区分: Int?,
    var 入出庫区分: Int?,
    var 商品KEY: Int?,
    var 保管倉庫KEY: Int?,
    var ロケーション: String?,
    var ロット番号: String?,
    var 入庫日付: String?,
    var 入出荷数量１: Int?,
    var 入出荷数量２: Int?,
    var 入出荷済数量１: Int?,
    var 入出荷済数量２: Int?,
    var 摘要: String?,
    var 商品ピッキング区分: Int?,
    var 届先ピッキング区分: Int?,
    var 倉庫ピッキング区分: Int?,
    var 保管料計算開始日: String?,
    var 賞味期限: String?,
    var 確定区分: Int?,
    var 転送区分: Int?,
    var 登録担当者: Int?,
    var 登録日時: String?,
    var 更新担当者: Int?,
    var 更新日時: String?,
    var 削除日付: String?
)


data class T17_NSKH_Master(
    var 入出庫番号: Int?,
    var 伝票区分: Int?,
    var バッチ番号: String?,
    var 荷主伝票番号: String?,
    var 入出庫日付: String?,
    var 得意先KEY: Int?,
    var 時間指定区分: Int?,
    var 時間指定: String?,
    var 元着区分: Int?,
    var 備考: String?,
    var 集約日付: String?,
    var 納品日付: String?,
    var 荷主KEY: Int?,
    var 出荷先KEY: Int?,
    var 出荷先郵便番号: String?,
    var 出荷先名１: String?,
    var 出荷先名２: String?,
    var 出荷先住所１: String?,
    var 出荷先住所２: String?,
    var 出荷先電話番号: String?,
    var コースKEY: Int?,
    var 運送便KEY: Int?,
    var 納品書発行区分: Int?,
    var 送り状発行区分: Int?,
    var 荷札発行枚数: Int?,
    var 個口数: Int?,
    var 重量: Int?,
    var 入力区分: Int?,
    var 登録担当者: Int?,
    var 登録日時: String?,
    var 更新担当者: Int?,
    var 更新日時: String?,
    var 削除日付: String?
)


data class T17_NSKDH_Master(
    var 入出庫番号: Int?,
    var 行番号: Int?,
    var 得意先KEY: Int?,
    var 商品KEY: Int?,
    var ロケーション: String?,
    var ロット番号: String?,
    var 入出荷数量１: Int?,
    var 入出荷数量２: Int?,
    var 入出荷済数量１: Int?,
    var 入出荷済数量２: Int?,
    var 賞味期限: String?,
    var JANコード: String?,
    var ロケーションFLAG: String?,
    var ロットFLAG: String?,
    var 入出庫区分: Int?,
    var 在庫数１: Int?,
    var 在庫数２: Int?
)

data class M72_TNT_List(val M72_TNT: List<M72_TNT_Master>)
data class M22_SOUK_List(var M22_SOUK: List<M22_SOUK_Master>)
data class M09_HIN_List(var M09_HIN: List<M09_HIN_Master>)
data class T17_NSKD_List(var T17_NSKD: List<T17_NSKD_Master>)
data class T17_NSKH_List(var T17_NSKH: List<T17_NSKH_Master>)