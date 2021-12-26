package com.im.versechat.data.local.Messages

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessagesCacheEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val chat_created_at: String?,
    val toId: String?,
    val fromId: String?
)
