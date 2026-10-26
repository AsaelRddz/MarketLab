package com.marketlab.di

import com.marketlab.data.repository.ProductRepositoryImpl
import com.marketlab.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Vincula ProductRepository con ProductRepositoryImpl para la inyección de dependencias.
 *
 * No llamamos a este módulo manualmente: Hilt procesa sus anotaciones durante la
 * compilación y genera el código que conecta las dependencias. Por eso puede no
 * aparecer en «buscar usos», aunque su vinculación se utiliza al crear el ViewModel.
 * Sin esta vinculación (u otra equivalente), Hilt no podría proporcionar ProductRepository.
 *
 * Flujo de resolución:
 * CatalogViewModel pide ProductRepository
 *     ↓ RepositoryModule define la implementación
 * ProductRepositoryImpl pide ProductApiService
 *     ↓ NetworkModule proporciona el servicio
 * ProductApiService
 */
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
