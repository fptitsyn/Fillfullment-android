package com.example.fillfullment.ui.order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fillfullment.FillfullmentTopAppBar
import com.example.fillfullment.ui.AppViewModelProvider
import com.example.fillfullment.ui.data.Order
import com.example.fillfullment.ui.navigation.EditOrderDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun EditOrderScreen(
    navigateBack: () -> Unit,
    viewModel: EditOrderViewmodel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.orderUiState
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            FillfullmentTopAppBar(
                screenTitle = EditOrderDestination.title,
                canNavigateBack = true,
                onClickBack = navigateBack
            )
        }
    ) { innerPadding ->
        EditOrderBody(
            uiState = uiState,
            viewModel = viewModel,
            coroutineScope = coroutineScope,
            navigateBack = navigateBack,
            onCheckedChange = viewModel::updateUiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun EditOrderBody(
    uiState: OrderUiState,
    viewModel: EditOrderViewmodel,
    coroutineScope: CoroutineScope,
    navigateBack: () -> Unit,
    onCheckedChange: (Order) -> Unit,
    modifier: Modifier = Modifier
) {
    var checked by remember { mutableStateOf(false) }
    var hasBeenChecked by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        OrderItem(
            order = uiState.orderDetails,
            modifier = modifier,
            showStatus = false
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        ) {
            Text(
                text = uiState.orderDetails.status,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1F)
            )
            Switch(
                checked = checked,
                onCheckedChange = {
                    hasBeenChecked = true
                    checked = it
                    if (checked) {
                        onCheckedChange(uiState.orderDetails.copy(status = "Завершён"))
                    } else {
                        onCheckedChange(uiState.orderDetails.copy(status = "В обработке"))
                    }
                },
                thumbContent = if (checked) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier
                                .size(SwitchDefaults.IconSize)
                                .weight(1F)
                        )
                    }
                }
                else {
                    null
                }
            )
        }
        Button(
            onClick = {
                coroutineScope.launch {
                    viewModel.updateOrder()
                    navigateBack()
                }
            },
            enabled = hasBeenChecked,
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Apply")
        }
    }
}