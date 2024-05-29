package jp.co.taika.kyoueihtapplication.ui.theme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import jp.co.taika.kyoueihtapplication.function.UserDataBase
import jp.co.taika.kyoueihtapplication.function.WorkerData_R

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SampleViewModel(application: Application):AndroidViewModel(application) {
//    private val dao:WorkerRoom
//    init {
//        //DBにアクセスするクラスで一度だけDBをビルドする
//        val db = UserDataBase.getDatabase(application)
//        //使用するdaoを指定
//        dao=db.workerdataDao()
//    }
//
//    //DBに保管された内容を表示
//    val workerData=dao.getWorkerAll()
//
//    //DBから表示
//    fun select(worker:String){
//        viewModelScope.launch (Dispatchers.IO){
//            dao.getWorkerAll()
//            WorkerData_R(
//                id=0,
//                name="name",
//                delete_at = "delete_at"
//            )
//        }
//    }

}