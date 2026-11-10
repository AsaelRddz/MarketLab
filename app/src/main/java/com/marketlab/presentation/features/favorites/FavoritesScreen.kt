package com.marketlab.presentation.features.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marketlab.domain.model.Product
import com.marketlab.presentation.components.ErrorContent
import com.marketlab.presentation.components.LoadingContent
import com.marketlab.presentation.components.ProductCard
import com.marketlab.presentation.theme.MarketLabTheme

@Composable
fun FavoritesRoute(viewModel: FavoritesViewModel = hiltViewModel(), openItem: (Int) -> Unit) {
    val favoritesState by viewModel.favoritesState.collectAsStateWithLifecycle()

    FavoritesScreen(
        favoritesState = favoritesState,
        onRetry = {
            viewModel.observeFavorites()
        },
        openItem = openItem
    )
}

@Composable
fun FavoritesScreen(
    favoritesState: FavoriteUIState,
    onRetry: () -> Unit,
    openItem: (Int) -> Unit
) {
    when (val state = favoritesState) {
        FavoriteUIState.Loading -> LoadingContent()
        is FavoriteUIState.Error -> ErrorContent(
            errorMessage = state.errorMessage,
            onRetry = onRetry
        )

        is FavoriteUIState.Success -> {
            if (state.favorites.isNotEmpty()) {
                FavoritesContent(
                    modifier = Modifier,
                    products = state.favorites,
                    openItem = openItem
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("You don’t have any favorites yet.")
                }
            }
        }
    }
}

@Composable
fun FavoritesContent(
    modifier: Modifier = Modifier,
    products: List<Product>,
    openItem: (Int) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            text = "Favorites",
            modifier = modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )

        Spacer(modifier = modifier.height(16.dp))

        LazyColumn {
            items(items = products, key = { it.id }) { product ->
                ProductCard(
                    modifier = modifier,
                    title = product.title,
                    price = product.price,
                    openItem = { openItem(product.id) }
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun FavoritesScreenPreview() {
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
        FavoritesScreen(
            favoritesState = FavoriteUIState.Success(products),
            onRetry = {},
            openItem = {}
        )
    }
}