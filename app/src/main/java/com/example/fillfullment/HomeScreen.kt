package com.example.fillfullment

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fillfullment.ui.AppViewModelProvider
import com.example.fillfullment.ui.data.Order

@Composable
fun HomeScreen() {
    RenderUsers()
}

@Composable
fun RenderUsers(viewmodel: HomeViewmodel = viewModel(factory = AppViewModelProvider.Factory)) {
    var data by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        data = viewmodel.getAllOrders()
        Log.d("MyLog", data)
    }

    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(
            items = viewmodel.convertJsonToOrderArray(data),
            key = { order ->
                order.order_id
            }
        ) { order ->
            Card(modifier = Modifier.padding(8.dp)) {
                OrderItem(
                    order = order
                )
            }
        }
    }
}

@Composable
fun OrderItem(
    order: Order
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = order.order_id.toString())
        Text(text = order.status)
        Text(text = order.type)
        for (detail in order.details) {
            Text(text = detail)
        }
        Text(text = order.total.toString())
    }
}