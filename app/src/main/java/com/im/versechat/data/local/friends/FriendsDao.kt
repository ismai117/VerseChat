package com.im.versechat.data.local.friends

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendsDao {

    @Query("SELECT * FROM friends")
    fun getFriends(): Flow<List<FriendsCacheEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(friendsCacheEntity: FriendsCacheEntity)

    @Query("DELETE FROM friends")
    fun deleteAllFriends()

}