package com.example.fillfullment.ui.data

data class User(
    val id: Int = 0,
    val last_name: String = "",
    val first_name: String = "",
    val middle_name: String = "",
    val emailOrPhone: String = "",
    val password: String = "",
    val status: Int = 2
)