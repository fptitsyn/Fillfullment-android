package com.example.fillfullment.ui.order

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fillfullment.FillfullmentTopAppBar
import com.example.fillfullment.ui.AppViewModelProvider
import com.example.fillfullment.ui.navigation.EditOrderDestination

@Composable
fun EditOrderScreen(
    navigateBack: () -> Unit,
    viewModel: EditOrderViewmodel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.orderUiState

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
            navigateBack = navigateBack,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun EditOrderBody(
    uiState: OrderUiState,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d("MyOrder", uiState.orderDetails.toString())
    OrderItem(
        order = uiState.orderDetails,
        modifier = modifier
    )
}