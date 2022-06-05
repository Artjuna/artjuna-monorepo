package com.artjuna.artjuna_app.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.artjuna.artjuna_app.core.data.source.local.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class ProductDatabase:RoomDatabase() {

    abstract fun productDao():ProductDao

    companion object{
        private const val DATABASE_NAME = "product.db"

        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun getInstance(context: Context): ProductDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    DATABASE_NAME
                ).build().apply {
                    INSTANCE = this
                }
            }
    }

}