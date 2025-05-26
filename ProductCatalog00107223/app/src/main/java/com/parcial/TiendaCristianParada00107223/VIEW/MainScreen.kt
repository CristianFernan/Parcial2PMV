package com.parcial.TiendaCristianParada00107223.VIEW

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.parcial.TiendaCristianParada00107223.MODEL.Product
import com.parcial.TiendaCristianParada00107223.VIEWMODEL.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, viewModel: ProductViewModel){
    //Lista de productos
    val productList by viewModel.filteredProducts.collectAsState()
    // Variable donde se guarda los datos de busqueda
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tienda de Bebidas") }
            )
        }
    ){
            paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ){
            Text(
                text = "Bebidas disponibles:",
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Barra de busqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                label =  { Text("Buscar bebida...")},
                modifier = Modifier.fillMaxWidth()
            )


            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    navController.navigate("cartH")
                }
            ) {
                Text("Ver carrito")
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (productList.isEmpty()) {
                Text("No hay perros disponibles", color = Color.Red)
            }
            else {
                // Display de la lista

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ){
                    items(productList) { product ->
                        ProductCard(product = product){
                            navController.navigate("detail/${product.id}")
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onClick: () -> Unit){
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable{onClick()},
        elevation = CardDefaults.cardElevation(4.dp),

        ) {
        Row(modifier = Modifier.padding(8.dp)){
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier =  Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(product.name, fontWeight = FontWeight.Bold)
                Text("Precio:  $${product.price}")
                product.category.forEach { category ->
                    Text(text = "Categoría: $category")
                }
                Text(product.description, maxLines = 2)
            }
        }
    }
}