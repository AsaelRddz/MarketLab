package com.marketlab.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Define la tabla favorite_products de Room: cada instancia representa una fila
 * y sus propiedades son las columnas. El id es la clave primaria de cada producto.
 */
@Entity(tableName = "favorite_products")
data class FavoriteProductEntity (
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val imageUrl: String
)
