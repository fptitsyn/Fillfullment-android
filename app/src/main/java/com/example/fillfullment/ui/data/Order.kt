package com.example.fillfullment.ui.data

data class Order(
    val order_id: Int = 0,
    val date: String = "",
    val status: String = "",
    val details: List<String> = listOf(),
    val type: String = "",
    val user_id: Int = 0,
    val total: Double = 0.0
)
