package com.artjuna.artjuna_app.core.data.source.local.room

import androidx.room.*
import com.artjuna.artjuna_app.core.data.source.local.entity.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM productentities")
    fun getListProductInCart():List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductToCart(productEntity: ProductEntity)

    @Query("DELETE FROM productentities WHERE id = :id")
    fun deleteProductInCartById(id: String)

    @Transaction
    @Query("SELECT * FROM productentities WHERE id = :id")
    fun getProductInCartById(id:String):List<ProductEntity>

}