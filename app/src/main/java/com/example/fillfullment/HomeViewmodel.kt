package com.example.fillfullment

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

    suspend fun getAllUsers(): String {
        return withContext(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            response.body!!.string()
        }
    }


}