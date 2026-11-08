package com.marketlab.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marketlab.data.local.entity.FavoriteProductEntity
import kotlinx.coroutines.flow.Flow

/**
 * Define las operaciones para acceder a los productos favoritos en la base de datos.
 * Aquí declararemos cómo guardarlos, consultarlos y eliminarlos; Room generará
 * la implementación de esos métodos a partir de sus anotaciones.
 */
@Dao
interface FavoriteProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: FavoriteProductEntity)

    /**
     * Flow emite la lista inicial y vuelve a consultar cuando cambia la tabla de favoritos.
     * No lleva suspend porque llamar a esta función solo devuelve el Flow; Room ejecuta
     * la consulta de forma asíncrona cuando se recolecta. La recolección sí usa una coroutine.
     */
    @Query("SELECT * FROM favorite_products")
    fun showFavorites(): Flow<List<FavoriteProductEntity>>

    @Delete
    suspend fun delete(product: FavoriteProductEntity)
}