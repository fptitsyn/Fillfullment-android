package com.example.fillfullment

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class HomeViewmodel : ViewModel() {
    private val client = OkHttpClient()
    private val request = Request.Builder()
        .url("http://10.0.2.2:5000/users/json")
        .get()
        .build()

//    var jsonText: MutableLiveData<String> = MutableLiveData()
    suspend fun getAllUsers(): String {
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: okhttp3.Call, e: IOException) {
//                jsonText.postValue(e.message.toString())
//                Log.d("MyLog", jsonText.toString())
//                callback(e.message.toString())
//            }
//
//            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
//                response.use {
//                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
//                    jsonText.postValue(response.body!!.string())
//                    Log.d("MyLog", jsonText.toString())
//                    callback(response.body!!.string())
//                }
//            }
//        })

        return withContext(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            response.body!!.string()
        }

    }

    fun convertJsonToUserArray(jsonList: String): Array<User> {
        if (jsonList.isBlank()) {
            return emptyArray()
        }

        val gson = Gson()
        val arrayUserType = object : TypeToken<Array<User>>() {}.type
        val users: Array<User> = gson.fromJson(jsonList, arrayUserType)
        Log.d("MyLog1", users.toString())
        return users
    }
}