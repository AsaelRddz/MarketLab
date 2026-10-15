package com.marketlab.domain.repository

import com.marketlab.domain.model.Product

interface ProductRepository {
    fun getProducts() : List<Product>
}