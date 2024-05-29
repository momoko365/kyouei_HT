package jp.co.taika.kyoueihtapplication.home

import Nyuko_Data_Save
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.co.taika.kyoueihtapplication.function.Nyuko
import jp.co.taika.kyoueihtapplication.function.Strings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {

    private val nyuko = Nyuko()
    private lateinit var nyukoNum: String
    private val activityScope = CoroutineScope(Dispatchers.Main)
    private val _startmessage = MutableLiveData<String>()
    private val _finishmessage = MutableLiveData<String>()

    var nyuko_Data = Nyuko_Data_Save


    fun next_btn() {
        activityScope.launch {
            nyukoNum =
                nyuko.getNyuSyukko(Strings.NYUSYUKKO_URL, nyuko_Data.selected_goods.toInt())
                    .toString()
            if (nyukoNum.isEmpty().not()) {
                _startmessage.value = "success"
            } else {
                _startmessage.value = "error"
            }
        }
    }

    val startTimeText: LiveData<String> get() = _startmessage
    val finishTimeText: LiveData<String> get() = _finishmessage

}