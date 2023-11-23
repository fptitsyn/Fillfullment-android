package com.example.fillfullment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.fillfullment.ui.navigation.FillfullmentNavHost
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

                    FillfullmentNavHost(
                        navController = navController
                    )
                }
            }
        }
    }
}

//@Composable
//fun RenderUsers(viewmodel: HomeViewmodel = viewModel()) {
//    var data by remember { mutableStateOf("") }
//
//    LaunchedEffect(Unit) {
//        data = viewmodel.getAllUsers()
//        Log.d("MyLog", data)
//    }
//
//    LazyColumn(modifier = Modifier.padding(8.dp)) {
//        items(
//            items = viewmodel.convertJsonToUserArray(data),
//            key = { it.id }
//        ) { user ->
//            Card(modifier = Modifier.padding(8.dp)) {
//                UserItem(
//                    id = user.id,
//                    username = user.username,
//                    password = user.password,
//                    status = user.status
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun UserItem(
//    id: Int,
//    username: String,
//    password: String,
//    status: String
//) {
//    Column(modifier = Modifier.padding(8.dp)) {
//        Text(text = id.toString())
//        Text(text = username)
//        Text(text = password)
//        Text(text = status)
//    }
//}
