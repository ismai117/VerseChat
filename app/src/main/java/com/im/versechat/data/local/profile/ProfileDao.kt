package com.im.versechat.data.local.profile

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile")
    fun getProfile(): Flow<ProfileCacheEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profileCacheEntity: ProfileCacheEntity)

    @Query("DELETE FROM profile")
    fun deleteProfile()

}