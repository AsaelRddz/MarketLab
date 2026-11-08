package com.marketlab.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marketlab.data.local.dao.FavoriteProductDao
import com.marketlab.data.local.entity.FavoriteProductEntity

@Database(entities = [FavoriteProductEntity::class], version = 1)
abstract class MarketLabDatabase : RoomDatabase() {
    abstract fun favoriteProductDao() : FavoriteProductDao
}