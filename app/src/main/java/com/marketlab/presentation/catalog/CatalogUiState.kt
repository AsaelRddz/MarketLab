package com.marketlab.presentation.catalog

import com.marketlab.domain.model.Product

/*data class CatalogUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val errorMessage: String? = null
)*/

sealed class CatalogUiState {
    data object Loading : CatalogUiState()
    data class Success(val products: List<Product>) : CatalogUiState()
    data class Error(val errorMessage: String) : CatalogUiState()
}