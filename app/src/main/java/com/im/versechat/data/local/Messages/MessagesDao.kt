package com.im.versechat.data.local.Messages

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface MessagesDao {

    @Query("SELECT * FROM messages")
    fun getMessages(): Flow<List<MessagesCacheEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(messagesCacheEntity: MessagesCacheEntity)

    @Query("DELETE FROM messages")
    fun deleteAllMessages()

}