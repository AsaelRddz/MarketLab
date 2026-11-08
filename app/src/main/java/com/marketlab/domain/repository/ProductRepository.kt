package com.marketlab.domain.repository

import com.marketlab.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts() : List<Product>
    suspend fun getProductById(productId : Int) : Product

    suspend fun addFavorite(product: Product)
    suspend fun removeFavorite(product: Product)
    fun observeFavorites(): Flow<List<Product>>
}