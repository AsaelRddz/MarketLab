package com.marketlab.presentation.features.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.marketlab.presentation.components.ErrorContent
import com.marketlab.presentation.components.LoadingContent
import com.marketlab.presentation.components.NoticeDialog
import com.marketlab.presentation.theme.MarketLabTheme


@Composable
fun DetailRoute(viewModel: DetailViewModel = hiltViewModel(), productId: Int?) {
    LaunchedEffect(productId) {
        viewModel.getProductById(productId)
    }

    val detailState by viewModel.detailState.collectAsStateWithLifecycle()

    DetailScreen(
        detailState = detailState,
        onRetry = {
            viewModel.getProductById(productId)
        },
        clickFavorite = {
            viewModel.toggleFavorite(productId)
        },
        onDismissMessageFavorite = viewModel::onDismissMessageFavorite,
        onRetryFavorites = {
            productId?.let(viewModel::observeFavorites)
        }
    )
}

@Composable
fun DetailScreen(
    detailState: DetailUiState,
    onRetry: () -> Unit,
    clickFavorite: () -> Unit,
    onDismissMessageFavorite: () -> Unit,
    onRetryFavorites: () -> Unit,
) {
    when (val state = detailState) {
        DetailUiState.Loading -> LoadingContent()

        is DetailUiState.Error -> ErrorContent(
            errorMessage = state.errorMessage,
            onRetry = onRetry
        )

        is DetailUiState.Success -> {
            DetailContent(
                modifier = Modifier,
                product = state.product,
                isFavorite = state.isFavorite,
                isFavoriteActionEnabled = !state.isUpdatingFavorite && state.favoriteReadState == FavoriteReadState.Ready,
                favoriteReadState = state.favoriteReadState,
                onRetryFavorites = onRetryFavorites,
                clickFavorite = clickFavorite
            )

            val message = state.favoritesObservationError ?: state.favoriteMessage

            message?.let {
                NoticeDialog(
                    message = it,
                    onDismiss = onDismissMessageFavorite
                )
            }
        }
    }
}

@Composable
fun DetailContent(
    modifier: Modifier,
    product: Product,
    isFavorite: Boolean,
    isFavoriteActionEnabled: Boolean,
    favoriteReadState: FavoriteReadState,
    onRetryFavorites: () -> Unit,
    clickFavorite: () -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            text = "Detail",
            modifier = modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )

        Spacer(modifier = modifier.height(16.dp))

        Text(
            text = product.title,
            modifier = modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Text(
            text = "$${product.price}",
            modifier = modifier.fillMaxWidth(),
            fontSize = 16.sp
        )

        Text(
            text = product.description,
            modifier = modifier.fillMaxWidth(),
            fontSize = 16.sp
        )

        Spacer(modifier.weight(1f))

        if (favoriteReadState == FavoriteReadState.Error){
            TextButton({
                onRetryFavorites()
            }) {
                Text("Retry loading favorites")
            }
        }  else if (favoriteReadState == FavoriteReadState.Loading) {
            Text("Loading favorites...")
        }

        Spacer(modifier.height(6.dp))

        Button(
            onClick = clickFavorite,
            enabled = isFavoriteActionEnabled,
            modifier = modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            val text = if (isFavorite) "Remove from favorites" else "Add to favorites"
            Text(text)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DetailScreenPreview() {
    val product = Product(
        id = 1,
        title = "Urban backpack",
        description = "Backpack for everyday use",
        price = 599.0,
        imageUrl = ""
    )

    MarketLabTheme {
        DetailScreen(
            detailState = DetailUiState.Success(product, isFavorite = true),
            onRetry = {},
            clickFavorite = {},
            onDismissMessageFavorite = {},
            onRetryFavorites = {}
        )
    }
}