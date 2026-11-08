package com.marketlab.di

import android.app.Application
import androidx.room.Room
import com.marketlab.data.local.MarketLabDatabase
import com.marketlab.data.local.dao.FavoriteProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    /**
     * Indica a Hilt cómo crear MarketLabDatabase cuando una clase la solicite.
     * Hilt proporciona Application como contexto y, gracias a @Singleton,
     * reutiliza la misma instancia de la base de datos durante la vida de la aplicación.
     * databaseBuilder configura la clase y el archivo local "marketlab_database";
     * build() crea la instancia de Room. Este método no inserta ni consulta favoritos.
     */
    @Provides
    @Singleton
    fun provideDatabase(application: Application): MarketLabDatabase {
        return Room.databaseBuilder(
            application,
            MarketLabDatabase::class.java,
            "marketlab_database",
        ).build()
    }

    @Provides
    fun provideFavoriteProductDao(database: MarketLabDatabase) : FavoriteProductDao {
        return database.favoriteProductDao()
    }
}
