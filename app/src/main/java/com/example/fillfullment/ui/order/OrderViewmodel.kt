package com.example.fillfullment.ui.order

import androidx.lifecycle.ViewModel
import com.example.fillfullment.ui.data.Order
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class OrderViewmodel: ViewModel() {

    private val client = OkHttpClient()

    suspend fun getAllOrders(): String {
        return withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url("http://10.0.2.2:5000/panel/orders/all")
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

        val gson = Gson()
        val arrayOrderType = object : TypeToken<Array<Order>>() {}.type
        val orders: Array<Order> = gson.fromJson(data, arrayOrderType)
        return orders
    }
}