package com.marketlab.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marketlab.data.local.dao.FavoriteProductDao
import com.marketlab.data.local.dao.ProductDao
import com.marketlab.data.local.entity.FavoriteProductEntity
import com.marketlab.data.local.entity.ProductEntity

@Database(entities = [FavoriteProductEntity::class, ProductEntity::class], version = 2)
abstract class MarketLabDatabase : RoomDatabase() {
    abstract fun favoriteProductDao() : FavoriteProductDao
    abstract fun productDao() : ProductDao
}