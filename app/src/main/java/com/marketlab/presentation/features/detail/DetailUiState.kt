package com.marketlab.presentation.features.detail

import com.marketlab.domain.model.Product

sealed class DetailUiState {
    data object Loading : DetailUiState()
    data class Success(
        val product: Product,
        val isFavorite: Boolean = false,
        val isUpdatingFavorite: Boolean = false,
        val messageFavorite: String? = null,
        val favoriteReadState: FavoriteReadState = FavoriteReadState.Loading,
        val favoritesObservationError: String? = null
    ) : DetailUiState()

    data class Error(val errorMessage: String) : DetailUiState()
}

enum class FavoriteReadState {
    Loading,
    Ready,
    Error
}