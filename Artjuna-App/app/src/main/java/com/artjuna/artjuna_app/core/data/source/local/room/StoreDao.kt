package com.artjuna.artjuna_app.core.data.source.local.room

import androidx.room.*
import com.artjuna.artjuna_app.core.data.source.local.entity.PostEntity
import com.artjuna.artjuna_app.core.data.source.local.entity.ProductEntity
import com.artjuna.artjuna_app.core.data.source.local.entity.StoreEntity

@Dao
interface StoreDao {

    @Query("SELECT * FROM storeentities")
    fun getAllStoreFollowed():List<StoreEntity>

    @Query("SELECT id FROM storeentities")
    fun getAllStoreFollowedId():List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStoreFollowed(storeEntity: StoreEntity)

    @Query("DELETE FROM storeentities WHERE id = :id")
    fun deleteStoreFollowedById(id: String)

    @Transaction
    @Query("SELECT * FROM storeentities WHERE id = :id")
    fun getStoreFollowedById(id:String):List<StoreEntity>

    @Query("DELETE FROM storeentities")
    fun deleteAllData()

}