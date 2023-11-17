package com.example.fillfullment

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                    RenderUsers()
                }
            }
        }
    }
}

@Composable
fun RenderUsers(viewmodel: HomeViewmodel = HomeViewmodel()) {
    var data by remember { mutableStateOf("") }
    
    LaunchedEffect(Unit) {
        data = viewmodel.getAllUsers()
        Log.d("MyLog", data)
    }

    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(
            items = viewmodel.convertJsonToUserArray(data),
            key = { it.id }
        ) { user ->
            Card(modifier = Modifier.padding(8.dp)) {
                UserItem(
                    id = user.id,
                    username = user.username,
                    password = user.password,
                    status = user.status
                )
            }
        }
    }
}

@Composable
fun UserItem(
    id: Int,
    username: String,
    password: String,
    status: String
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = id.toString())
        Text(text = username)
        Text(text = password)
        Text(text = status)
    }
}
