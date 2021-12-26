package com.im.versechat.data.local.profile

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileImageDao {


    @Query("SELECT * FROM profileimage")
    fun getProfileImage(): Flow<ProfileImageCacheEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profileImageCacheEntity: ProfileImageCacheEntity)

    @Query("DELETE FROM profileimage")
    fun deleteProfileImage()

}