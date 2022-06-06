package com.artjuna.artjuna_app.core.data.source.local.room

import androidx.room.*
import com.artjuna.artjuna_app.core.data.source.local.entity.PostEntity
import com.artjuna.artjuna_app.core.data.source.local.entity.ProductEntity

@Dao
interface PostDao {

    @Query("SELECT * FROM posttentities")
    fun getAllPostLiked():List<PostEntity>

    @Query("SELECT id FROM posttentities")
    fun getAllPostLikedId():List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPostLiked(postEntity: PostEntity)

    @Query("DELETE FROM posttentities WHERE id = :id")
    fun deletePostLikedById(id: String)

    @Transaction
    @Query("SELECT * FROM posttentities WHERE id = :id")
    fun getPostLikedById(id:String):List<PostEntity>

    @Query("DELETE FROM posttentities")
    fun deleteAllData()

}