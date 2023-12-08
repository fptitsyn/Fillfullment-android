package com.example.fillfullment.ui.order

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fillfullment.FillfullmentTopAppBar
import com.example.fillfullment.R
import com.example.fillfullment.ui.AppViewModelProvider
import com.example.fillfullment.ui.data.Order
import com.example.fillfullment.ui.data.UserStore
import com.example.fillfullment.ui.navigation.OrdersDestination
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navigateToOrderEdit: (Int) -> Unit,
    onLogOut: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val store = UserStore(context)
    var logOutConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            FillfullmentTopAppBar(
                screenTitle = OrdersDestination.title,
                canNavigateBack = false,
                onClickBack = {},
                onLogOut = {
                    logOutConfirmationRequired = true
                }
            )
        }
    ) { innerPadding ->
        RenderUsers(
            modifier = Modifier.padding(innerPadding),
            onItemClick = { navigateToOrderEdit(it.order_id) }
        )

        if (logOutConfirmationRequired) {
            LogOutConfirmationDialog(
                onLogOutConfirm = {
                    coroutineScope.launch {
                        store.saveUserData("", "")
                    }
                    onLogOut()
                },
                onLogOutCancel = {
                    logOutConfirmationRequired = false
                },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun RenderUsers(
    onItemClick: (Order) -> Unit,
    modifier: Modifier = Modifier,
    viewmodel: OrderViewmodel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var data by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        data = viewmodel.getAllOrders()
    }

    LazyColumn(modifier = modifier) {
        items(
            items = viewmodel.convertJsonToOrderArray(data),
            key = { it.order_id }
        ) { order ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
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
                Text(text = "x${product.quantity} ${product.name} | Артикул #${product.model_number} " +
                        "| Цена: ${product.price} | Сумма: ${product.subtotal}")
            }
            if (!order.label.isNullOrBlank()) {
                Text(text = "Этикетка: ${order.label}")
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

@Composable
fun LogOutConfirmationDialog(
    onLogOutConfirm: () -> Unit,
    onLogOutCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = stringResource(R.string.log_out_confirmation_title)) },
        text = { Text(text = stringResource(R.string.log_out_confirmation_text)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onLogOutCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onLogOutConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        }
    )
}