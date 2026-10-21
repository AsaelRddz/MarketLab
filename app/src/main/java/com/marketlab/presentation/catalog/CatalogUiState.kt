package com.marketlab.presentation.catalog

import com.marketlab.domain.model.Product

data class CatalogUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val errorMessage: String? = null
)