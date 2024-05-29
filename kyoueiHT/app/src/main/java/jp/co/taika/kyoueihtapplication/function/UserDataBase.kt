package jp.co.taika.kyoueihtapplication.function

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

//@Databaseアノテーションでデータベースであることを認識させる、引数にエンティティとデータベースのバージョン（マイグレーション時に必要）、バックアップの有無を指定
@Database(
    entities = [NyukoData_R::class, WorkerData_R::class, SoukoData_R::class, ShohinData_R::class, NyukoDetailData_R::class, NyukoHeaderData_R::class,QRDataList::class],
    version = 9 ,exportSchema = false
)
//RoomDatabaseクラスを継承
abstract class UserDataBase :RoomDatabase(){
    //daoへのアクセスを提供（？）
    abstract fun shohindao():ShohinDAO
    abstract fun qrdatalistdao():QRDatalistdAO
//    abstract  fun workerDao():WorkerDAO
//    abstract fun soukoDao():SoukoDAO
//
//    abstract fun nyukodetailDao():NyukoDetailDAO
//    abstract fun nyukoheaderDao():NyukoHeaderDAO


//    companion object{
//        @Volatile
//        private var INSTANCE:UserDataBase?=null
//
//        fun getDatabase(context: Context):UserDataBase{
////もしINSTANCEがnullでない場合、すでにデータベースのインスタンスが存在するためそのインスタンスを返します。。
//            //もしINSTANCEがnullである場合、まだデータベースのインスタンスが作成されていないので新しいデータベースのインスタンスを作成します。
//            return INSTANCE ?: synchronized(this){
//                val instance =Room.databaseBuilder(
//                    context.applicationContext,
//                    UserDataBase::class.java,
//                    "user_database"
//                )
//                    .addCallback(object : Callback() {
//                            override fun onCreate(db: SupportSQLiteDatabase) {
//                                super.onCreate(db)
//                                val sql =
//                                    "INSERT INTO 'WorkerData_R' (id, name, delete_at) VALUES (NULL, '田中', 'a')"
//                                db.execSQL(sql)
//                            }
//                        })
//                    .addMigrations(MIGRATION_1_2)
//                    //、fallbackToDestructiveMigration() メソッドは、データベースのスキーマが変更された場合に、データベースを破棄して再構築する方法を指定
//                    .fallbackToDestructiveMigration()
//                    .build()
//                INSTANCE=instance
//                instance
//            }
//        }
//    }
//    }
//
//object MIGRATION_1_2 : Migration(1,2) {
//    override fun maigrate(dataBase: SupportSQLiteDatabase){
//        dataBase.execSQL()
//    }

}


////抽象メソッドからdaoを取得できるようにする
//abstract class UserDataBase : RoomDatabase() {
//    abstract fun workerdataDao(): WorkerRoom
//
//    //シングルトンの実装：companion objectはインスタンス化しなくてもアクセスできるもの。シングルトンを実装するためにその中に自信をインスタンス化して取得するメソッドを定義
//    companion object {
//        private const val DB_NAME = "taikadb"
//
//        @Volatile
//        private var INSTANCE: UserDataBase? = null
//        fun getDatabase(context: Context): UserDataBase {
//            synchronized(this) {
//                if (INSTANCE == null) {
//                    //ROOMのビルダー
//                    val instance = Room.databaseBuilder(
//                        //アプリのコンキテキストを取得
//                        context.applicationContext,
//                        //RoomDBのデータアクセスクラスを指定
//                        UserDataBase::class.java,
//                        //データクラスの名前を指定
//                        DB_NAME
//                    )
//                        .addCallback(object : Callback() {
//                            override fun onCreate(db: SupportSQLiteDatabase) {
//                                super.onCreate(db)
//                                val sql =
//                                    "INSERT INTO 'WorkerData_R' (id, name, delete_at) VALUES (NULL, '田中', 'a')"
//                                db.execSQL(sql)
//                            }
//                        })
//                        .build()
//                    INSTANCE = instance
//
//                }
//                return INSTANCE!!
//
//            }
//
//        }
//    }
//
//    //    abstract fun nyukodataDao(): NyusyukkoDataRoom
//
////    abstract fun soukodataDao(): SoukoRoom
////    abstract fun shohindataDao(): ShohinRoom
////    abstract fun nyukodetailDao(): NyukoDetailRoom
////    abstract fun nyukoheaderDao(): NyukoHeaderRoom
//
//}