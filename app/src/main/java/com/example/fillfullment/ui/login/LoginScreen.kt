package com.example.fillfullment.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fillfullment.R
import com.example.fillfullment.ui.data.User
import com.example.fillfullment.ui.data.UserStore
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onClick: () -> Unit,
    viewmodel: LoginViewmodel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val store = UserStore(context)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LoginInputForm(
            userDetails = viewmodel.userUiState.userDetails,
            onValueChange = viewmodel::updateUiState
        )
        Button(
            onClick = {
                coroutineScope.launch {
                    val id = viewmodel.checkUserInDb()
                    if (id != "") {
                        store.saveUserData(viewmodel.userUiState.userDetails.emailOrPhone.trim(), viewmodel.userUiState.userDetails.password.trim(), id.toInt())
                        onClick()
                    }
                }
            },
            enabled = viewmodel.userUiState.isEntryValid,
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier
                .padding(top = 64.dp)
                .fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.login))
        }
    }
}

@Composable
fun LoginInputForm(
    userDetails: User,
    modifier: Modifier = Modifier,
    onValueChange: (User) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        TextField(
            value = userDetails.emailOrPhone,
            onValueChange = { onValueChange(userDetails.copy(emailOrPhone = it)) },
            label = { Text(text = stringResource(R.string.email_or_phone_number)) },
            colors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.onSurface,
                focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = userDetails.password,
            onValueChange = { onValueChange(userDetails.copy(password = it)) },
            visualTransformation = PasswordVisualTransformation(),
            label = { Text(text = stringResource(R.string.password)) },
            colors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.onSurface,
                focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}