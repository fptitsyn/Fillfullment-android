package com.example.fillfullment.ui.order

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fillfullment.ui.data.Order
import com.example.fillfullment.ui.navigation.EditOrderDestination
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class EditOrderViewmodel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val orderId: Int = checkNotNull(savedStateHandle[EditOrderDestination.orderIdArg])

    private val client = OkHttpClient()

    var orderUiState by mutableStateOf(OrderUiState())
        private set

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val request = Request.Builder()
                    .url("http://10.0.2.2:5000/panel/orders/$orderId")
                    .get()
                    .build()

                val response = client.newCall(request).execute()
                val order = convertJsonToOrder(response.body!!.string())
                Log.d("OrderUi", order.toString())
                orderUiState = order.toOrderUiState()
                Log.d("OrderUi", orderUiState.orderDetails.toString())
            }
        }
    }

    private fun validateInput(uiState: Order = orderUiState.orderDetails) : Boolean {
        return with(uiState) {
            status.isNotBlank()
        }
    }

    fun updateUiState(orderDetails: Order) {
        orderUiState = OrderUiState(orderDetails = orderDetails, isEntryValid = validateInput(orderDetails))
    }

    private fun convertJsonToOrder(json: String) : Order {
        if (json.isBlank()) {
            return Order()
        }
        Log.d("MyOrder", json)
        val gson = Gson()
        val order: Order = gson.fromJson(json, Order::class.java)
        return order
    }

    suspend fun updateOrder() {
        withContext(Dispatchers.IO) {
            val json = """
                {
                    "status": "${orderUiState.orderDetails.status}"
                }
            """.trimIndent()

            val body = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val request = Request.Builder()
                .url("http://10.0.2.2:5000/panel/orders/$orderId/edit")
                .patch(body)
                .build()

            val response = client.newCall(request).execute()
            Log.d("ResponseStatus", response.body!!.string())
        }
    }
}

data class OrderUiState(
    val orderDetails: Order = Order(),
    val isEntryValid: Boolean = false
)

fun Order.toOrderUiState(isEntryValid: Boolean = false) : OrderUiState = OrderUiState(
    orderDetails = this,
    isEntryValid = isEntryValid
)