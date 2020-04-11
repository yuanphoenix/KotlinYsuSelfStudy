package com.example.ysuselfstudy.logic


import android.util.Log
import androidx.lifecycle.liveData
import com.example.ysuselfstudy.network.EmptyRoomNetWork
import kotlinx.coroutines.Dispatchers
import org.litepal.LitePal
import java.lang.Exception

/**
 * @author  Ahyer
 * @date  2020/4/11 11:18
 * @version 1.0
 */
object Repository {

    private val TAG ="Repository"
    fun getRoom(query: String) = liveData(Dispatchers.IO) {

        var result = try {
            Log.d(TAG, "");
            val room = EmptyRoomNetWork.searchRoom(query)
            LitePal.saveAll(room)
            Result.success(room)
        } catch (e: Exception) {
            Log.d(TAG,e.toString())
        }
        emit(result)
    }
}