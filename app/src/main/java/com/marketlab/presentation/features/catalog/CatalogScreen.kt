package com.marketlab.presentation.features.catalog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marketlab.domain.model.Product
import com.marketlab.presentation.components.ProductCard
import com.marketlab.presentation.components.ErrorContent
import com.marketlab.presentation.components.LoadingContent
import com.marketlab.presentation.theme.MarketLabTheme

@Composable
fun CatalogRoute(
    openItem: (Int) -> Unit,
    viewModel: CatalogViewModel = hiltViewModel(),
    openFav: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getProducts()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CatalogScreen(
        uiState = uiState,
        onRetry = viewModel::getProducts,
        openItem = openItem,
        openFav = openFav
    )
}

@Composable
fun CatalogScreen(
    uiState: CatalogUiState,
    onRetry: () -> Unit,
    openItem: (Int) -> Unit,
    openFav: () -> Unit
) {
    when (val state = uiState) {
        CatalogUiState.Loading -> LoadingContent()

        is CatalogUiState.Error -> ErrorContent(
            errorMessage = uiState.errorMessage,
            onRetry = onRetry
        )

        is CatalogUiState.Success -> {
            CatalogContent(
                modifier = Modifier,
                products = state.products,
                openItem = openItem,
                openFav = openFav
            )
        }
    }
}


@Composable
fun CatalogContent(
    modifier: Modifier = Modifier,
    products: List<Product>,
    openItem: (Int) -> Unit,
    openFav: () -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            text = "Catalog",
            modifier = modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )

        Spacer(modifier = modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(items = products, key = { it.id }) { product ->
                ProductCard(
                    modifier = modifier,
                    title = product.title,
                    price = product.price,
                    openItem = { openItem((product.id)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = openFav,
            modifier = modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Ver favoritos")
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
            openItem = {},
            openFav = {}
        )
    }
}
