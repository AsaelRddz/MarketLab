package com.marketlab.presentation.features.catalog

import com.marketlab.domain.model.Product
import com.marketlab.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class FakeProductRepository(
    private val products: List<Product>,
    var exception: Exception? = null
) : ProductRepository {
    override suspend fun getProducts(): List<Product> {
        exception?.let { throw it }
        return products
        // return if (exception != null) throw exception else products
    }

    override suspend fun getProductById(productId: Int): Product {
        error("No se usa en esta prueba")
    }

    override suspend fun addFavorite(product: Product) {
        error("No se usa en esta prueba")
    }

    override suspend fun removeFavorite(product: Product) {
        error("No se usa en esta prueba")
    }

    override fun observeFavorites(): Flow<List<Product>> {
        error("No se usa en esta prueba")
    }
}