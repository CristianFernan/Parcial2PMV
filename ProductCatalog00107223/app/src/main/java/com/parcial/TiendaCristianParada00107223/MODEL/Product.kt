package com.parcial.TiendaCristianParada00107223.MODEL

data class Product(
    val id: Int,
    val name: String,
    val category: List<String>,
    val price: Double,
    val imageUrl: String,
    val description: String,
    val onCart: Boolean
)
