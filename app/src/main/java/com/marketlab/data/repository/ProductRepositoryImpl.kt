package com.marketlab.data.repository

import com.marketlab.data.remote.ProductApiService
import com.marketlab.domain.model.Product
import com.marketlab.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApiService: ProductApiService
) : ProductRepository {

    /**
     * .map transforma cada ProductResponse de la API en un Product, el modelo que usan tus pantallas.
     */
    override suspend fun getProducts(): List<Product> {
        val response = productApiService.getAllProducts()

        return response.products.map {
            Product(
                id = it.id,
                title = it.title,
                description = it.description,
                price = it.price,
                imageUrl = it.thumbnail
            )
        }
    }
}