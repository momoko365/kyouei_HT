package jp.co.taika.kyoueihtapplication.ui.theme

import Start_Data_Save
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.room.Room
import jp.co.taika.kyoueihtapplication.R
import jp.co.taika.kyoueihtapplication.function.UserDataBase


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.NumberFormatException

//StartWorkerDayからの遷移
class Start_Worker_Name : ComponentActivity() {



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        // データベースの初期化とビルドを非同期で行う
//        GlobalScope.launch {
//            // データベースの初期化処理
//            withContext(Dispatchers.IO) {
//                db = Room.databaseBuilder(
//                    applicationContext,
//                    UserDataBase::class.java,
//                    "taika.db"
//                ).fallbackToDestructiveMigration().build()
//
//                // WorkerDAOの初期化
//                dao = db.workerDao()
//            }
//            // 画面表示処理をメインスレッドで行う
//            withContext(Dispatchers.Main) {
//                //画面デザインとの紐づけ
//                setContentView(R.layout.start_worker_name)
//                //画面デザインの入力項目やボタンとの紐づけ
//                var worker_ID = findViewById<EditText>(R.id.worker_ID)
//                var next_btn = findViewById<Button>(R.id.next_btn)
//                var back_btn = findViewById<Button>(R.id.back_btn)
//                var start = Start_Data_Save
//
//
//                //エンターキーを押すと画面遷移できるようにする
//                fun handleKeyEvent(view: View, keyCode: Int): Boolean {
//                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
//                        next_btn.performClick()
//                        return true
//                    }
//                    return false
//                }
//                worker_ID.setOnKeyListener { view, keyCode, _ ->
//                    handleKeyEvent(view, keyCode)
//                }
//                GlobalScope.launch {
//                    withContext(Dispatchers.IO) {
//                        var worker = WorkerData_R(id = 0, name = "nakae", "momoka")
//                        dao.insert(worker)
//                    }
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(applicationContext, "OK", Toast.LENGTH_LONG).show()
//                    }
//                }
//
//
//                //次へボタンが押された時の処理
//                next_btn.setOnClickListener {
//                    try {
//                        //入力された作業者コードを取得する
//                        val workerId = worker_ID.text.toString().toInt()
//
//                        //非同期スコープ内で処理を実行
//                        GlobalScope.launch {
//                            val targetWorker = withContext(Dispatchers.IO) {
//                                dao.getAll(workerId)
//                            }
//                            withContext(Dispatchers.Main) {
//                                targetWorker.observe(this@Start_Worker_Name, Observer { worker ->
//                                    if (worker != null) {
//                                        //WorkerData_Rオブジェクトがnullでないことを確認した後でidを取得する
//                                        if (worker.id == workerId) {
//                                            val intent = Intent(
//                                                this@Start_Worker_Name,
//                                                Start_Main_Menu::class.java
//                                            )
//                                            startActivity(intent)
//                                            finish()
//                                        } else {
//                                            val intent = Intent(
//                                                this@Start_Worker_Name,
//                                                Error_Message::class.java
//                                            )
//                                            val message = "コードの誤りです"
//                                            intent.putExtra("ERROR_KEY", message)
//                                            startActivity(intent)
//                                        }
//                                    } else {
//                                        val intent = Intent(
//                                            this@Start_Worker_Name,
//                                            Error_Message::class.java
//                                        )
//                                        val message = "コードの誤りです"
//                                        intent.putExtra("ERROR_KEY", message)
//                                        startActivity(intent)
//                                    }
//                                })
//
//                            }
//                        }
//                    }catch (e:NumberFormatException){
//                        val intent = Intent(this@Start_Worker_Name, Error_Message::class.java)
//                        val message = "入力漏れです"
//                        intent.putExtra("ERROR_KEY", message)
//                        startActivity(intent)
//                    }
////                    try { //非同期スコープ内で処理を開始
////
////                        //入力された作業者コードを取得
//////                        start.worker_id = worker_ID.text.toString().toInt()
////                        val workerId = worker_ID.text.toString().toInt()
////                        GlobalScope.launch {
////                            val targetworker = withContext(Dispatchers.IO) {
////                                dao.getAll(workerId)
////                            }
////                            if (targetworker!=null) {
////                                withContext(Dispatchers.Main) {
////                                    //mainメニューに遷移
////                                    val intent =
////                                        Intent(this@Start_Worker_Name, Start_Main_Menu::class.java)
////                                    startActivity(intent)
////                                    finish()
////                                }
////                            } else {
////                                //workerが存在しない場合はエラーメッセージを表示
////                                val intent =
////                                    Intent(this@Start_Worker_Name, Error_Message::class.java)
////                                val message = "コードの誤りです"
////                                intent.putExtra("ERROR_KEY", message)
////                                startActivity(intent)
////                            }
////
////                        }
////
////
//////                } else {
////                        //workerが存在しない場合はエラーメッセージを表示
//////                    val intent = Intent(this, Error_Message::class.java)
//////                    val message = "エラーです"
//////                    intent.putExtra("ERROR_KEY", message)
//////                    startActivity(intent)
//////                }
////
////
////                    } catch (e: NumberFormatException) {
////                        //worker_idが数字出ない場合、エラーメッセージを表示
////                        val intent = Intent(this@Start_Worker_Name, Error_Message::class.java)
////                        val message = "入力漏れです"
////                        intent.putExtra("ERROR_KEY", message)
////                        startActivity(intent)
////                    }
//                }
//
//                // 戻るボタンを押した時の処理
//                back_btn.setOnClickListener {
//                    val intent = Intent(this@Start_Worker_Name, Start_Work_Day::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//            }
//
//
//        }


    }
}
