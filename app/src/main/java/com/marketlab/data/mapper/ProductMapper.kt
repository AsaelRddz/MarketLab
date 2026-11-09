package com.marketlab.data.mapper

import com.marketlab.data.local.entity.ProductEntity
import com.marketlab.data.remote.dto.ProductResponse
import com.marketlab.domain.model.Product

/**
 * Convierte la respuesta de la API al modelo de dominio Product.
 * Usa thumbnail como imageUrl para que el dominio no dependa del nombre usado por la API.
 */
internal fun ProductResponse.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        price = price,
        imageUrl = thumbnail
    )
}

internal fun ProductResponse.toEntity() : ProductEntity {
    return ProductEntity(
        id = id,
        title = title,
        description = description,
        price = price,
        imageUrl = thumbnail
    )
}

internal fun ProductEntity.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        price = price,
        imageUrl = imageUrl
    )
}