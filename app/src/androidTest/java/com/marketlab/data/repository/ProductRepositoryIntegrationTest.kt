package com.marketlab.data.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.marketlab.data.local.MarketLabDatabase
import com.marketlab.data.local.entity.ProductEntity
import com.marketlab.data.remote.FakeProductApiService
import com.marketlab.domain.model.Product
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

// AndroidJUnit4 ejecuta la clase como prueba instrumentada.
@RunWith(AndroidJUnit4::class)
class ProductRepositoryIntegrationTest {
    private lateinit var database: MarketLabDatabase

    // @Before crea una base vacía para cada prueba.
    @Before
    fun setUp() {
        // inMemoryDatabaseBuilder mantiene los datos en memoria durante la prueba.
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MarketLabDatabase::class.java
        ).build()
    }

    // @After cierra la base y libera sus recursos.
    @After
    fun testDown() {
        database.close()
    }

    @Test
    fun getProducts_returnsCachedProductsWhenNetworkFails() = runTest {
        val cachedProduct = ProductEntity(
            id = 1,
            title = "Mochila",
            description = "Mochila urbana",
            price = 599.0,
            imageUrl = ""
        )

        // Guarda un producto antes de simular la consulta sin conexión.
        database.productDao().insertProduct(cachedProduct)

        val repository = ProductRepositoryImpl(
            productApiService = FakeProductApiService(),
            favoriteProductDao = database.favoriteProductDao(),
            productDao = database.productDao()
        )

        // El servicio falso falla y el repositorio consulta Room.
        val result = repository.getProducts()

        assertEquals(
            listOf(Product(1, "Mochila", "Mochila urbana", 599.0, "")),
            result
        )
    }
}
