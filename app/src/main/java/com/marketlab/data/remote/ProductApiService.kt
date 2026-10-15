package com.marketlab.data.remote

import com.marketlab.data.model.AllProductsResponse
import com.marketlab.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {

    //.- suspend permite llamar estas funciones desde una coroutine sin bloquear mientras espera la respuesta.

    @GET("products")
    suspend fun getAllProducts(): AllProductsResponse

    //.- @Path sustituye {id} por el ID recibido.

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductResponse
}