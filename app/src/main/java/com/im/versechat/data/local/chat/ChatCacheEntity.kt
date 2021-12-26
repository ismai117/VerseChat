package com.im.versechat.data.local.chat

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat")
data class ChatCacheEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val message: String?,
    val toId: String?,
    val fromId: String?
)
