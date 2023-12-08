package com.example.fillfullment.ui.data

data class Order(
    val date: String = "",
    val details: List<Product> = listOf(),
    val label: String? = "",
    val order_id: Int = 0,
    val status: String = "",
    val total: Double = 0.0,
    val type: String = "",
    val user_id: Int = 0
)
