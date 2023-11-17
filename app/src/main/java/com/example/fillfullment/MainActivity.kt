package com.example.fillfullment

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fillfullment.ui.theme.FillfullmentTheme
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

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
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("http://10.0.2.2:5000/users")
        .get()
        .build()

    var text: String

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: okhttp3.Call, e: IOException) {
            text = e.message.toString()
            Log.d("MyLog", text)
        }
        override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
            response.use {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                text = response.body!!.string()
                Log.d("MyLog", text)
            }
        }
    })
}
