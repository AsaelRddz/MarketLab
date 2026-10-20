package com.marketlab.di

import com.marketlab.data.repository.ProductRepositoryImpl
import com.marketlab.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Registra esta clase como un módulo de configuración de Hilt.
@Module
// Hace disponible esta vinculación en el contenedor de la aplicación.
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Cuando una clase solicite ProductRepository, Hilt entregará ProductRepositoryImpl.
     * Hilt puede crear la implementación porque su constructor tiene @Inject;
     * sus dependencias, como ProductApiService, también deben estar registradas.
     *
     * @Binds declara la vinculación sin escribir un cuerpo: Hilt genera el código.
     * @Singleton reutiliza una instancia por contenedor de aplicación, creada al necesitarse.
     * Este método no consulta la API; solo configura la inyección de dependencias.
     */
    @Binds
    @Singleton
    abstract fun bindProductRepository(
        implementation : ProductRepositoryImpl
    ): ProductRepository

}
