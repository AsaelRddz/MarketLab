package com.marketlab.ui.catalog

import androidx.lifecycle.ViewModel
import com.marketlab.domain.model.Product
import com.marketlab.data.repository.ProductRepositoryImpl
import com.marketlab.domain.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CatalogViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    init {
        getProducts()
    }

    fun getProducts() {
        val repository: ProductRepository = ProductRepositoryImpl()
        val products = repository.getProducts()
        _products.value = products
    }
}