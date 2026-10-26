package com.marketlab.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marketlab.domain.model.Product
import com.marketlab.presentation.catalog.CatalogUiState
import com.marketlab.presentation.catalog.CatalogViewModel
import com.marketlab.presentation.components.ErrorContent
import com.marketlab.presentation.components.LoadingContent


@Composable
fun DetailRoute(productId: Int?, viewModel: CatalogViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DetailScreen(
        uiState = uiState,
        productId = productId,
        onRetry = viewModel::getProducts
    )
}

@Composable
fun DetailScreen(
    uiState: CatalogUiState,
    productId: Int?,
    onRetry: () -> Unit
) {
    when (val state = uiState) {
        CatalogUiState.Loading -> LoadingContent()

        is CatalogUiState.Error -> ErrorContent(
            errorMessage = uiState.errorMessage,
            onRetry = onRetry
        )

        is CatalogUiState.Success -> {
            DetailContent(
                modifier = Modifier,
                products = state.products,
                productId = productId
            )
        }
    }
}

@Composable
fun DetailContent(modifier: Modifier, products: List<Product>, productId: Int?) {
    Column(
        modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        val product = products.find { it.id == productId } ?: return@Column

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
    }
}

@Preview(showSystemUi = true)
@Composable
fun DetailScreenPreview() {
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

    DetailScreen(
        uiState = CatalogUiState.Success(products),
        productId = 1,
        onRetry = {}
    )
}