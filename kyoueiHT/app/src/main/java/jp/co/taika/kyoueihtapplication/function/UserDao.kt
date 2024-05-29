package jp.co.taika.kyoueihtapplication.function

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow



@Dao
interface QRDatalistdAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert (vararg qrDataList: QRDataList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll (qrDataList: List<QRDataList>)

    @Query("UPDATE QRDataList SET zumi = :zumi WHERE shohinCD = :shohinCD")
    fun updateZumi(zumi: Int, shohinCD: String)

    @Query("SELECT * FROM QRDataList")
    fun getAll(): List<QRDataList>

    @Query("SELECT * FROM QRDataList Where shohinCD= :shohin")
    fun getShohinCD(shohin:String):QRDataList?

    @Query("SELECT * FROM QRDataList Where seizo_day = :seizo_day")
    fun getShohiniroiro(seizo_day: String):QRDataList?


    @Query("DELETE FROM QRDataList")
    fun deleteAll()
}

@Dao
interface ShohinDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert (vararg shohindataR: ShohinData_R)
    //データベース上の全てのShohinData_Rを取得
    @Query("SELECT * FROM ShohinData_R")
    fun getShohinAll(): ShohinData_R

    @Query("SELECT * FROM ShohinData_R WHERE shohinCD= :shohin")
    fun getShohinCD(shohin:String):ShohinData_R

}

//@Dao
//interface NyusyukkoDataRoom {
//    //データベース上の全てのNyukoData_Rを取得
//    @Query("SELECT * FROM NyukoData_R")
//    fun getNyukoAll(): List<NyukoData_R>
//
//}
//
//@Dao
//interface WorkerDAO {
//    //データベース上の全てのWorkerData_Rを取得
//    //id指定する必要があるかどうかわからんけど一応
//    @Insert
//    fun insert(workerdataR: WorkerData_R)
//    //SQL分が引数になる
//    @Query("SELECT * FROM WorkerData_R where id = :id")
//    //Flowつけたら非同期データを観察できるらしいいるかどうかわからんけど一応
//    //戻り値は<WorkerData_R>
//    fun getAll(id:Int): LiveData<WorkerData_R>
//
//
//
//}
//
//@Dao
//interface SoukoDAO {
//    @Insert
//    fun insert(soukodataR: SoukoData_R)
//    //データベース上の全てのSoukoData_Rを取得
//    @Query("SELECT * FROM SoukoData_R WHERE id= :key" )
//    fun getSoukoAll(key:Int): SoukoData_R
//
//}
//
//
//
//@Dao
//interface NyukoDetailDAO {
//    //データベース上の全てのNyukoDetailData_Rを取得
//    @Query("SELECT * FROM NyukoDetailData_R")
//    fun getNyukoDetailAll(): NyukoDetailData_R
//
//
//}
//
//@Dao
//interface NyukoHeaderDAO {
//    //データベース上の全てのNyukoHeaderData_Rを取得
//    @Query("SELECT * FROM NyukoHeaderData_R")
//    fun getNyukoHeaderAll(): NyukoHeaderData_R
//
//
//}
//
