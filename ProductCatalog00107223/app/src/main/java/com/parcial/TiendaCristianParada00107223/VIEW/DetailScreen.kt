package com.parcial.TiendaCristianParada00107223.VIEW

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.parcial.TiendaCristianParada00107223.MODEL.Product
import com.parcial.TiendaCristianParada00107223.VIEWMODEL.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, product: Product, viewModel: ProductViewModel){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de ${product.name}") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Nombre: ${product.name}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Precio: $${product.price}", fontSize = 18.sp)
            product.category.forEach { category ->
                Text(text = "Categoría: $category")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Descripción:", fontWeight = FontWeight.Bold)
            Text(product.description)
            // Sacar el context de Android actual
            val context =  LocalContext.current

            Button(onClick = {
                viewModel.addToCart(product.id)
                Toast.makeText(
                    context,
                    "${product.name} was added to cart",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
                Text("Agregar al carrito")
            }
        }
    }
}