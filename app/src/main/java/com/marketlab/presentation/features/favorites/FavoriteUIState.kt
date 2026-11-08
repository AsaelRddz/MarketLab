package com.marketlab.presentation.features.favorites

import com.marketlab.domain.model.Product

sealed class FavoriteUIState {
    data object Loading : FavoriteUIState()

    data class Success(
        val favorites: List<Product> = emptyList()
    ) : FavoriteUIState()

    data class Error(val errorMessage: String) : FavoriteUIState()
}