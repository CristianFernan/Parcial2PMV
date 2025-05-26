package com.parcial.TiendaCristianParada00107223.VIEWMODEL

import android.util.Log
import com.parcial.TiendaCristianParada00107223.MODEL.Product
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel: ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products
    // Hacer busquedas
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // Listra filtrada en base a las queries
    val filteredProducts: StateFlow<List<Product>> = combine(_products, _searchQuery) { products, query ->
        if (query.isBlank()) {
            products
        } else {
            products.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.category.any { cat -> cat.contains(query, ignoreCase = true) }
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    //Simular un fetching
    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch{
            try {
                //Crear datos mock
                val mockProducts = List(10) { index ->
                    Product(
                        id = index + 1,
                        name = listOf("Dr. Pepper","Fanta","Redbull","BeLight","Pepsi","Red-Cola","Salutaris","Coca-Cola","Sprite","Colashampan")[index % 10],
                        category = listOf(
                            listOf("Dulce","Efervescente"),
                            listOf("Dulce","Energizante"),
                            listOf("Energizante","Saludable"),
                            listOf("Energizante","dulce"),
                            listOf("Energizante","dulce"),
                            listOf("Energizante","dulce"),
                            listOf("Energizante","dulce"),
                            listOf("Energizante","dulce"),
                            listOf("Energizante","dulce"),
                            listOf("Energizante","dulce"))[index % 10],
                        description = "Bebida refrescante para acompañar con comidas",
                        imageUrl = "https://elements-resized.envatousercontent.com/elements-cover-images/b84180ba-b256-4256-bfc6-8e28e9926bf8?w=433&cf_fit=scale-down&q=85&format=auto&s=767a9250c460a3fb33400b3d37e9af3d6ad4b79d4699b259a5aa4c1f949d656e",
                        price = 1.80,
                        onCart = false
                    )
                }

                _products.value = mockProducts
            }  catch (e: Exception) {
                Log.e("ProductViewModel", "Error al retraer datos: ${e.message}")

            }

        }
    }
    // Agregar cosas al cart
    fun addToCart(productId: Int) {
        _products.value = _products.value.map { product ->
            if (product.id == productId) product.copy(onCart = true) else product
        }
    }
}
