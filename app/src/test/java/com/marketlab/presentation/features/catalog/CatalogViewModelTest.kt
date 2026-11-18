package com.marketlab.presentation.features.catalog

import com.marketlab.MainDispatcherRule
import com.marketlab.domain.model.Product
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class CatalogViewModelTest {
    @get:Rule
    // Sustituye Dispatchers.Main para controlar las corrutinas del ViewModel
    // en cada prueba y restaura el dispatcher original al terminar.
    val mainDispatcherRule = MainDispatcherRule()

    // Success
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getProducts_emitsSuccessWhenRepositoryReturnsProducts() = runTest {
        // Aquí prepararás el repositorio falso, llamarás a getProducts()
        // y comprobarás que el estado sea Success.

        val products =
            listOf(
                Product(1, "Mochila", "Mochila urbana", 599.0, "")
            )

        val viewModel = CatalogViewModel(FakeProductRepository(products))

        viewModel.getProducts()

        // Ejecuta las coroutines pendientes del planificador de pruebas.
        advanceUntilIdle()

        // Comprueba que el estado del ViewModel sea Success con los productos esperados.
        assertEquals(CatalogUiState.Success(products), viewModel.uiState.value)
    }

    // IOException error
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getProducts_emitsConnectionErrorWhenRepositoryThrowsIOException() = runTest {
        val repository = FakeProductRepository(
            products = emptyList(),
            exception = IOException("Sin conexión")
        )
        val viewModel = CatalogViewModel(repository)

        viewModel.getProducts()
        advanceUntilIdle()

        assertEquals(
            CatalogUiState.Error("Could not load products. Check your connection."),
            viewModel.uiState.value
        )
    }

    // httpException error
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getProducts_emitsServerErrorWhenRepositoryThrowsHttpException() = runTest {
        val response = Response.error<List<Product>>(500, "Internal Server Error".toResponseBody())

        val repository = FakeProductRepository(
            products = emptyList(),
            exception = HttpException(response)
        )
        val viewModel = CatalogViewModel(repository)

        viewModel.getProducts()
        advanceUntilIdle()

        assertEquals(
            CatalogUiState.Error("The server could not complete the request. Please try again."),
            viewModel.uiState.value
        )
    }

    // reintento
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getProducts_emitsConnectionErrorAndThenSuccessWhenRepositoryThrowsIOExceptionFirst() = runTest {
        val products =
            listOf(
                Product(1, "Mochila", "Mochila urbana", 599.0, "")
            )

        val repository = FakeProductRepository(
            products = products,
            exception = IOException("Sin conexión")
        )
        val viewModel = CatalogViewModel(repository)

        viewModel.getProducts()
        advanceUntilIdle()

        assertEquals(
            CatalogUiState.Error("Could not load products. Check your connection."),
            viewModel.uiState.value
        )

        // simula que vuelve la conexion
        repository.exception = null
        viewModel.getProducts()
        advanceUntilIdle()

        assertEquals(CatalogUiState.Success(products), viewModel.uiState.value)
    }


}
