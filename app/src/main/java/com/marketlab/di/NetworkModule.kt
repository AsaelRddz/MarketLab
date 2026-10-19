package com.marketlab.di

import com.marketlab.data.remote.ProductApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Crea el cliente Retrofit que se usará para acceder a la API de DummyJSON.
     * La URL base es la dirección a la que se agregan las rutas del servicio.
     * Gson convierte las respuestas JSON en los modelos de Kotlin definidos en el servicio.
     * Este método solo configura el cliente; todavía no realiza una petición de red.
     *
     * @Provides indica a Hilt cómo obtener una instancia de Retrofit.
     * @Singleton hace que Hilt reutilice esa instancia durante la vida de la aplicación.
     */
    @Provides
    @Singleton
    fun provideRetrofitClient(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    /**
     * Crea la implementación de ProductApiService a partir de su interfaz.
     * Hilt entrega automáticamente el Retrofit configurado por provideRetrofitClient().
     * Retrofit interpreta las anotaciones del servicio para saber qué ruta consultar
     * y cómo convertir la respuesta cuando se llame a uno de sus métodos de petición.
     * Crear el servicio aquí todavía no descarga productos.
     *
     * @Provides permite que Hilt entregue este servicio a las clases que lo necesiten.
     * @Singleton hace que esas clases compartan la misma instancia del servicio.
     */
    @Provides
    @Singleton
    fun provideProductApiService(
        retrofit: Retrofit
    ): ProductApiService =
        retrofit.create(ProductApiService::class.java)

}