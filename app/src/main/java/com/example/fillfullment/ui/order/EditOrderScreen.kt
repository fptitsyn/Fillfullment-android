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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.example.fillfullment.ui.navigation.EditOrderDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun EditOrderScreen(
    navigateBack: () -> Unit,
    onLogOut: () -> Unit,
    viewModel: EditOrderViewmodel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.orderUiState
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val store = UserStore(context)
    var logOutConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            FillfullmentTopAppBar(
                screenTitle = EditOrderDestination.title,
                canNavigateBack = true,
                onClickBack = navigateBack,
                onLogOut = {
                    logOutConfirmationRequired = true
                }
            )
        }
    ) { innerPadding ->
        EditOrderBody(
            uiState = uiState,
            viewModel = viewModel,
            coroutineScope = coroutineScope,
            navigateBack = navigateBack,
            onCheckedChange = viewModel::updateUiState,
            snackbarHostState = snackbarHostState,
            modifier = Modifier.padding(innerPadding)
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
fun EditOrderBody(
    uiState: OrderUiState,
    viewModel: EditOrderViewmodel,
    coroutineScope: CoroutineScope,
    navigateBack: () -> Unit,
    onCheckedChange: (Order) -> Unit,
    snackbarHostState: SnackbarHostState,
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
                checked = uiState.orderDetails.status == "Завершён",
                onCheckedChange = {
                    hasBeenChecked = true
                    checked = it
                    if (checked) {
                        onCheckedChange(uiState.orderDetails.copy(status = "Завершён"))
                    } else {
                        onCheckedChange(uiState.orderDetails.copy(status = "В обработке"))
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = MaterialTheme.colorScheme.secondary
                ),
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
        if (!uiState.orderDetails.label.isNullOrBlank()) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.printLabel()
                        snackbarHostState.showSnackbar("Начало печати...")
                    }
                },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = stringResource(R.string.print_the_label))
            }
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
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = stringResource(R.string.apply))
        }
    }
}