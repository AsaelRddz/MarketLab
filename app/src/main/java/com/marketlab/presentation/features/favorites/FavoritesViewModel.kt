package com.marketlab.presentation.features.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marketlab.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _favoritesState =
        MutableStateFlow<FavoriteUIState>(FavoriteUIState.Loading)
    val favoritesState: StateFlow<FavoriteUIState> = _favoritesState.asStateFlow()
    private var favoritesJob: Job? = null

    init {
        observeFavorites()
    }

    fun observeFavorites() {
        favoritesJob?.cancel()
        _favoritesState.value = FavoriteUIState.Loading

        favoritesJob = viewModelScope.launch {
            try {
                repository.observeFavorites()
                    .collect { favorites ->
                        _favoritesState.value = FavoriteUIState.Success(
                            favorites = favorites
                        )
                    }
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                _favoritesState.value = FavoriteUIState.Error(
                    "Could not load favorites. Please try again."
                )
            }
        }
    }
}