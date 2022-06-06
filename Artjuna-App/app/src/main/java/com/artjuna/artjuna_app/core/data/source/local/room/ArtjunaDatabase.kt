package com.artjuna.artjuna_app.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.artjuna.artjuna_app.core.data.source.local.entity.PostEntity
import com.artjuna.artjuna_app.core.data.source.local.entity.ProductEntity
import com.artjuna.artjuna_app.core.data.source.local.entity.StoreEntity

@Database(entities = [ProductEntity::class, PostEntity::class, StoreEntity::class], version = 1, exportSchema = false)
abstract class ArtjunaDatabase:RoomDatabase() {

    abstract fun productDao():ProductDao
    abstract fun postDao():PostDao
    abstract fun storeDao():StoreDao

    companion object{
        private const val DATABASE_NAME = "artjuna.db"

        @Volatile
        private var INSTANCE: ArtjunaDatabase? = null

        fun getInstance(context: Context): ArtjunaDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ArtjunaDatabase::class.java,
                    DATABASE_NAME
                ).build().apply {
                    INSTANCE = this
                }
            }
    }

}