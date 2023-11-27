package com.example.fillfullment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.example.fillfullment.ui.data.UserStore
import com.example.fillfullment.ui.navigation.FillfullmentNavHost
import com.example.fillfullment.ui.navigation.LoginDestination
import com.example.fillfullment.ui.navigation.OrdersDestination
import com.example.fillfullment.ui.theme.FillfullmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FillfullmentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    val context = LocalContext.current
                    val store = UserStore(context)

                    val usernameToken = store.getUsernameToken.collectAsState(initial = "")
                    val passwordToken = store.getPasswordToken.collectAsState(initial = "")

                    var startDestination = LoginDestination.finalRoute

                    if (usernameToken.value.isNotBlank() && passwordToken.value.isNotBlank()) {
                        startDestination = OrdersDestination.finalRoute
                    }

                    FillfullmentNavHost(
                        navController = navController,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FillfullmentTopAppBar(
    screenTitle: String,
    canNavigateBack: Boolean,
    onClickBack: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onClickBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_icon)
                    )
                }
            }
        },
        title = {
            Text(text = screenTitle)
        }
    )
}