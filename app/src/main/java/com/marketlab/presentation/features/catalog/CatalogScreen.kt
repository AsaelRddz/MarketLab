package com.marketlab.presentation.features.catalog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarState
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
import com.marketlab.presentation.components.SearchContent
import com.marketlab.presentation.theme.MarketLabTheme

@Composable
fun CatalogRoute(
    viewModel: CatalogViewModel = hiltViewModel(),
    openItem: (Int) -> Unit,
    onOpenFavorites: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getProducts()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CatalogScreen(
        uiState = uiState,
        onRetry = viewModel::getProducts,
        openItem = openItem,
        onOpenFavorites = onOpenFavorites
    )
}

@Composable
fun CatalogScreen(
    uiState: CatalogUiState,
    onRetry: () -> Unit,
    openItem: (Int) -> Unit,
    onOpenFavorites: () -> Unit
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
                onOpenFavorites = onOpenFavorites
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogContent(
    modifier: Modifier = Modifier,
    products: List<Product>,
    openItem: (Int) -> Unit,
    onOpenFavorites: () -> Unit
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

        val searchState = rememberSearchBarState()
        val textState = rememberTextFieldState()

        // Conserva las coincidencias; si el texto está vacío, contains("") acepta todos.
        val filteredProducts = products.filter {
            it.title.contains(textState.text, ignoreCase = true)
                    || it.price.toString().contains(textState.text)
        }
        SearchContent(modifier = modifier, searchState, textState)

        Spacer(modifier = modifier.height(16.dp))

        if (filteredProducts.isEmpty()) Text("No products found")

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(items = filteredProducts, key = { it.id }) { product ->
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
            onClick = onOpenFavorites,
            modifier = modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("View favorites")
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
            title = "Urban backpack",
            description = "Backpack for everyday use",
            price = 599.0,
            imageUrl = ""
        ),
        Product(
            id = 2,
            title = "Wireless earbuds",
            description = "Earbuds with charging case",
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
            onOpenFavorites = {}
        )
    }
}
