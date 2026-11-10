package com.marketlab.presentation.features.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marketlab.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.io.IOException
import retrofit2.HttpException

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    // La pantalla comienza esperando la consulta de productos.
    private val _uiState =
        MutableStateFlow<CatalogUiState>(CatalogUiState.Loading)

    // Las pantallas pueden observar el estado, pero no modificarlo.
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()


    fun getProducts() {
        viewModelScope.launch {
            _uiState.value = CatalogUiState.Loading

            try {
                val products = repository.getProducts()

                // Entrega los productos a la pantalla.
                _uiState.value = CatalogUiState.Success(products)
            } catch (exception: IOException) {
                // Incluye fallos de conexión, DNS y tiempos de espera.
                _uiState.value = CatalogUiState.Error(
                    "Could not load products. Check your connection."
                )
            } catch (exception: HttpException) {
                // El servidor respondió con un código HTTP de error.
                _uiState.value = CatalogUiState.Error(
                    "The server could not complete the request. Please try again."
                )
            }
        }
    }
}
