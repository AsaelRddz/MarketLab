package com.marketlab.presentation.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marketlab.R
import com.marketlab.domain.model.Product
import com.marketlab.presentation.theme.MarketLabTheme

@Composable
fun CatalogRoute(openItem: (Int) -> Unit, viewModel: CatalogViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CatalogScreen(
        uiState = uiState,
        onRetry = viewModel::getProducts,
        openItem = openItem
    )
}

@Composable
fun CatalogScreen(uiState: CatalogUiState, onRetry: () -> Unit, openItem: (Int) -> Unit) {
    when(val state = uiState) {
        CatalogUiState.Loading -> {

        }
        is CatalogUiState.Error -> {

        }
        is CatalogUiState.Success -> {
            CatalogContent(
                modifier = Modifier,
                products = state.products,
                openItem = openItem,
            )
        }
    }
}


@Composable
fun CatalogContent(modifier: Modifier = Modifier, products: List<Product>, openItem: (Int) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            text = "Catalog",
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(products.size) { index ->
                ProductCard(
                    modifier = modifier,
                    title = products[index].title,
                    price = products[index].price,
                    openItem = { openItem((products[index].id)) }
                )
            }
        }
    }
}

@Composable
fun ProductCard(modifier: Modifier, title: String, price: Double, openItem: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(
                onClick = { openItem() }
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("$$price", fontSize = 16.sp)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CatalogScreenPreview() {
    // Datos locales para dibujar la pantalla sin Hilt, ViewModel ni conexión a internet.
    val products = listOf(
        Product(
            id = 1,
            title = "Mochila urbana",
            description = "Mochila para uso diario",
            price = 599.0,
            imageUrl = ""
        ),
        Product(
            id = 2,
            title = "Audífonos inalámbricos",
            description = "Audífonos con estuche de carga",
            price = 899.5,
            imageUrl = ""
        ),
    )

    MarketLabTheme {
        // Probamos el estado Success; los callbacks no realizan acciones en este preview.
        CatalogScreen(
            uiState = CatalogUiState.Success(products),
            onRetry = {},
            openItem = {}
        )
    }
}
