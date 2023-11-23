package com.example.fillfullment

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.fillfullment.ui.data.Order
import com.example.fillfullment.ui.data.User
import com.example.fillfullment.ui.navigation.HomeDestination
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class HomeViewmodel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val userId: Int = checkNotNull(savedStateHandle[HomeDestination.userIdArg])

    private val client = OkHttpClient()

    suspend fun getAllOrders(): String {
        return withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url("http://10.0.2.2:5000/panel/products/$userId")
                .get()
                .build()

            val response = client.newCall(request).execute()
            response.body!!.string()
        }
    }

    fun convertJsonToOrderArray(data: String): Array<Order> {
        if (data.isBlank()) {
            return emptyArray()
        }
        Log.d("MyLogData", data)
        val gson = Gson()
        val arrayOrderType = object : TypeToken<Array<Order>>() {}.type
        val orders: Array<Order> = gson.fromJson(data, arrayOrderType)
        return orders
    }
}