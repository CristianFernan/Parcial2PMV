package com.parcial.TiendaCristianParada00107223

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.parcial.TiendaCristianParada00107223.VIEW.CartScreen
import com.parcial.TiendaCristianParada00107223.VIEW.DetailScreen
import com.parcial.TiendaCristianParada00107223.VIEW.MainScreen
import com.parcial.TiendaCristianParada00107223.VIEWMODEL.ProductViewModel
import com.parcial.TiendaCristianParada00107223.VIEWMODEL.Screen
import com.parcial.TiendaCristianParada00107223.ui.theme.ProductCatalog00107223Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductCatalog00107223Theme {
                val navController = rememberNavController()
                val viewModel: ProductViewModel = viewModel()

                // Navegaci[on
                NavHost(
                    navController = navController,
                    startDestination = Screen.Main.route
                ) {
                    // Pagina Principal
                    composable(Screen.Main.route) {
                        MainScreen(navController, viewModel)
                    }
                    // Detalle Producto
                    composable(Screen.Cart.route) {
                        CartScreen(navController, viewModel)
                    }
                    // Carrito de productos
                    composable(
                        route = Screen.Details.route,
                        arguments = listOf(navArgument("productId") { type = NavType.IntType})
                    ){
                        backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId") ?: -1
                        val dog = viewModel.products.collectAsState().value.find { it.id == productId }

                        dog?.let {
                            DetailScreen(navController, product = it, viewModel)
                        }
                    }

                }
            }
        }
    }
}

