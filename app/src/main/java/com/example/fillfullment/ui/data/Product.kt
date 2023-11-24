package com.example.fillfullment.ui.data

data class Product(
    val count: Int = 0,
    val id: Int = 0,
    val name: String = "",
    val price: Double = 0.0,
    val quantity: Int = 0,
    val stock: Int = 0,
    val subtotal: Double = price * quantity,
    val uid: Int = 0,
    val unit: String = ""
)
