package com.marketlab.domain.repository

import com.marketlab.domain.model.Product

interface ProductRepository {
    suspend fun getProducts() : List<Product>
}