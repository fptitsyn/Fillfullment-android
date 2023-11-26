package com.example.fillfullment.ui.order

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.example.fillfullment.FillfullmentTopAppBar
import com.example.fillfullment.ui.AppViewModelProvider
import com.example.fillfullment.ui.data.Order
import com.example.fillfullment.ui.navigation.OrdersDestination

@Composable
fun HomeScreen(
    navigateToOrderEdit: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            FillfullmentTopAppBar(
                screenTitle = OrdersDestination.title,
                canNavigateBack = false,
                onClickBack = {}
            )
        }
    ) { innerPadding ->
        RenderUsers(
            modifier = Modifier.padding(innerPadding),
            onItemClick = { navigateToOrderEdit(it.order_id) }
        )
    }
}

@Composable
fun RenderUsers(
    onItemClick: (Order) -> Unit,
    modifier: Modifier = Modifier,
    viewmodel: HomeViewmodel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var data by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        data = viewmodel.getAllOrders()
        Log.d("MyLog", data)
    }

    LazyColumn(modifier = modifier) {
        items(
            items = viewmodel.convertJsonToOrderArray(data),
            key = { it.order_id }
        ) { order ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onItemClick(order) }
            ) {
                OrderItem(
                    order = order
                )
            }
        }
    }
}

@Composable
fun OrderItem(
    order: Order,
    modifier: Modifier = Modifier,
    showStatus: Boolean = true
) {
    Column(modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Заказ №${order.order_id}",
            style = MaterialTheme.typography.headlineSmall
        )
        Column(modifier = Modifier.padding(top = 4.dp, start = 4.dp)
        ) {
            Text(text = "Тип: ${order.type}")
            for (product in order.details) {
                Text(text = "x${product.quantity} ${product.name} " +
                        "| Цена: ${product.price} | Сумма: ${product.subtotal}")
            }
            Text(text = "Итого: ${order.total}")
            if (showStatus) {
                Text(
                    text = order.status,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}