package com.im.versechat.data.local.friends

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friends")
data class FriendsCacheEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String?,
    val phone: String?,
    val email: String?,
    val userId: String?,
    val image: String?,
    val status: String?,
)

