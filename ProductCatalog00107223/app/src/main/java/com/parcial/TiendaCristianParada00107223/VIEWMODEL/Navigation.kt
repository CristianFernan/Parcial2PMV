package com.parcial.TiendaCristianParada00107223.VIEWMODEL

sealed class Screen(val route: String, val title: String){
    object Main: Screen("home", "Shop")
    object Details: Screen("detail/{productId}", "Product detail")
    object Cart: Screen("cart", "My Cart")
}