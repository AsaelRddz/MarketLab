package com.marketlab.data.repository

import com.marketlab.data.local.dao.FavoriteProductDao
import com.marketlab.data.mapper.toEntity
import com.marketlab.data.mapper.toProduct
import com.marketlab.data.remote.ProductApiService
import com.marketlab.domain.model.Product
import com.marketlab.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApiService: ProductApiService,
    private val favoriteProductDao: FavoriteProductDao
) : ProductRepository {

    /**
     * .map transforma cada ProductResponse de la API en un Product, el modelo que usan tus pantallas.
     */
    override suspend fun getProducts(): List<Product> {
        return productApiService.getAllProducts().products.map { it.toProduct() }
    }

    override suspend fun getProductById(productId: Int): Product {
        return productApiService.getProductById(productId).toProduct()
    }


    /**
     * El primer map transforma cada lista emitida por el Flow de Room.
     * El segundo map recorre esa lista y convierte cada FavoriteProductEntity en Product.
     * Así pasamos de Flow<List<FavoriteProductEntity>> a Flow<List<Product>>
     * y seguimos recibiendo las actualizaciones de favoritos.
     */
    override fun observeFavorites(): Flow<List<Product>> {
        return favoriteProductDao.showFavorites().map {
            it.map { entity ->
                entity.toProduct()
            }
        }
    }

    override suspend fun addFavorite(product: Product) {
        favoriteProductDao.insert(product = product.toEntity())
    }

    override suspend fun removeFavorite(product: Product) {
        favoriteProductDao.delete(product.toEntity())
    }
}
