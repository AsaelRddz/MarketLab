package com.marketlab.data.remote

import com.marketlab.data.remote.dto.AllProductsResponse
import com.marketlab.data.remote.dto.ProductResponse
import java.io.IOException

class FakeProductApiService : ProductApiService {

    // Simula una falla de conexión al cargar el catálogo.
    override suspend fun getAllProducts(): AllProductsResponse {
        throw IOException("Sin conexión")
    }

    override suspend fun getProductById(id: Int): ProductResponse {
        error("Este método no se utiliza en esta prueba")
    }
}