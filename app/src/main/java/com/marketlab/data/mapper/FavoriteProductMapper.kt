package com.marketlab.data.mapper

import com.marketlab.data.local.entity.FavoriteProductEntity
import com.marketlab.domain.model.Product

/**
 * Convierte un Product en la entidad que Room utiliza para guardar un favorito.
 * Conserva el ID del producto como clave primaria de la fila.
 */
internal fun Product.toEntity(): FavoriteProductEntity {
    return FavoriteProductEntity(
        id = id,
        title = title,
        description = description,
        price = price,
        imageUrl = imageUrl
    )
}

/**
 * Convierte un favorito almacenado en Room al modelo de dominio Product.
 * Así, el repositorio entrega productos sin exponer entidades de Room a la presentación.
 */
internal fun FavoriteProductEntity.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        price = price,
        imageUrl = imageUrl
    )
}