package com.marketlab.presentation.features.detail

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marketlab.domain.model.Product
import com.marketlab.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.io.IOException
import retrofit2.HttpException

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    private val _detailState =
        MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailState: StateFlow<DetailUiState> = _detailState.asStateFlow()
    private var favoritesJob: Job? = null

    fun getProductById(productId: Int?) {
        // Un ID ausente o inválido no debe dejar la pantalla cargando indefinidamente.
        if (productId == null || productId <= 0) {
            _detailState.value = DetailUiState.Error("The product ID is invalid.")
            return
        }

        viewModelScope.launch {
            _detailState.value = DetailUiState.Loading

            try {
                val product = repository.getProductById(productId)

                _detailState.value = DetailUiState.Success(
                    product = product
                )
                observeFavorites(productId)
            } catch (exception: IOException) {
                _detailState.value = DetailUiState.Error(
                    "Could not load the product. Check your connection."
                )
            } catch (exception: HttpException) {
                // El servidor respondió con un código HTTP de error.
                _detailState.value = DetailUiState.Error(
                    "The server could not complete the request. Please try again."
                )
            }
        }
    }

    /**
     * Cancela la observación anterior y escucha los favoritos; catch maneja errores del Flow
     * anteriores a collect, mientras collect actualiza si el producto está en la lista (any).
     * En ambos update, el if exige un estado Success y el mismo ID para modificar solo
     * el producto correspondiente mediante copy. Si no se cumple, el else devuelve state
     * sin cambios: la última expresión de la lambda es el estado que update debe conservar.
     *
     * favorites.any { it.id == productId }
     *
     * Comprueba si hay al menos un producto en favorites cuyo ID coincida con productId. Devuelve
     * un Boolean.
     */
    fun observeFavorites(productId: Int) {
        favoritesJob?.cancel()

        _detailState.update { state ->
            if (state is DetailUiState.Success && state.product.id == productId) {
                state.copy(favoritesObservationError = null, favoriteReadState = FavoriteReadState.Loading)
            } else {
                state
            }
        }

        favoritesJob = viewModelScope.launch {
            repository.observeFavorites()
                .catch {
                    _detailState.update { state ->
                        if (state is DetailUiState.Success && state.product.id == productId) {
                            state.copy(
                                favoritesObservationError = "Could not load favorites.",
                                favoriteReadState = FavoriteReadState.Error
                            )
                        } else {
                            state
                        }
                    }
                }
                .collect { favorites ->
                    _detailState.update { state ->
                        if (state is DetailUiState.Success && state.product.id == productId) {
                            state.copy(
                                isFavorite = favorites.any { it.id == productId },
                                favoritesObservationError = null,
                                favoriteReadState = FavoriteReadState.Ready
                            )
                        } else {
                            state
                        }
                    }
                }
        }
    }

    fun toggleFavorite(productId: Int?) {
        val state = _detailState.value
        if (state is DetailUiState.Success && state.product.id == productId) {
            val product = state.product
            updateFavorite(product)
        }
    }

    private fun updateFavorite(product: Product) {
        val state = _detailState.value
        if (state is DetailUiState.Success && state.product.id == product.id &&
            !state.isUpdatingFavorite && state.favoriteReadState == FavoriteReadState.Ready
        ) {
            _detailState.value = state.copy(isUpdatingFavorite = true)
            if (state.isFavorite) {
                removeFavorite(product)
            } else {
                addFavorite(product)
            }
        }
    }

    private fun addFavorite(product: Product) {
        viewModelScope.launch {
            try {
                repository.addFavorite(product)

                _detailState.update { state ->
                    if (state is DetailUiState.Success && state.product.id == product.id) {
                        state.copy(
                            isFavorite = true,
                            favoriteMessage = "Added to favorites."
                        )
                    } else {
                        state
                    }
                }
            } catch (exception: SQLiteException) {
                _detailState.update { state ->
                    if (state is DetailUiState.Success &&
                        state.product.id == product.id
                    ) {
                        state.copy(
                            favoriteMessage = "Could not add to favorites."
                        )
                    } else {
                        state
                    }
                }
            } finally {
                finishFavoriteUpdate(product.id)
            }
        }
    }

    private fun removeFavorite(product: Product) {
        viewModelScope.launch {
            try {
                repository.removeFavorite(product)

                _detailState.update { state ->
                    if (state is DetailUiState.Success && state.product.id == product.id) {
                        state.copy(
                            isFavorite = false,
                            favoriteMessage = "Removed from favorites."
                        )
                    } else {
                        state
                    }
                }
            } catch (exception: SQLiteException) {
                _detailState.update { state ->
                    if (state is DetailUiState.Success &&
                        state.product.id == product.id
                    ) {
                        state.copy(
                            favoriteMessage = "Could not remove from favorites."
                        )
                    } else {
                        state
                    }
                }
            } finally {
                finishFavoriteUpdate(product.id)
            }
        }
    }

    private fun finishFavoriteUpdate(productId: Int) {
        _detailState.update { state ->
            if (state is DetailUiState.Success &&
                state.product.id == productId
            ) {
                state.copy(isUpdatingFavorite = false)
            } else {
                state
            }
        }
    }

    fun onDismissMessageFavorite() {
        _detailState.update { state ->
            if (state is DetailUiState.Success) {
                state.copy(favoriteMessage = null, favoritesObservationError = null)
            } else {
                state
            }
        }
    }
}
