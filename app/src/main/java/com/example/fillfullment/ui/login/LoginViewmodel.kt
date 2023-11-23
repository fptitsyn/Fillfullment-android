package com.example.fillfullment.ui.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fillfullment.ui.data.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class LoginViewmodel : ViewModel() {

    private val client = OkHttpClient()

    var userUiState by mutableStateOf(UserUiState())
        private set

    fun updateUiState(userDetails: User) {
        userUiState = UserUiState(userDetails = userDetails, isEntryValid = validateInput(userDetails))
    }

    private fun validateInput(uiState: User = userUiState.userDetails): Boolean {
        return with(uiState) {
            username.isNotBlank()
            password.isNotBlank()
        }
    }

    suspend fun checkUserInDb() : Boolean {
        if (!validateInput()) {
            return false
        }

        return withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url("http://10.0.2.2:5000/users/json")
                .get()
                .build()

            val response = client.newCall(request).execute()
            val json = response.body!!.string()

            val users = convertJsonToUserArray(json)

            val currentUser = userUiState.userDetails
            for (user in users) {
                if (currentUser == user && user.status == 2) {
                    userUiState = user.toUserUiState()
                    return@withContext true
                }
            }

            return@withContext false
        }
    }
}

fun convertJsonToUserArray(jsonList: String): Array<User> {
    if (jsonList.isBlank()) {
        return emptyArray()
    }

    val gson = Gson()
    val arrayUserType = object : TypeToken<Array<User>>() {}.type
    val users: Array<User> = gson.fromJson(jsonList, arrayUserType)
    return users
}

data class UserUiState(
    val userDetails: User = User(),
    val isEntryValid: Boolean = false
)

fun User.toUserUiState(isEntryValid: Boolean = false) : UserUiState = UserUiState(
    userDetails = this,
    isEntryValid = isEntryValid
)