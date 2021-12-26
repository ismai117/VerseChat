package com.im.versechat.data.local.users

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UsersCacheEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String?,
    val phone: String?,
    val email: String?,
    val userId: String?,
    val image: String?,
    val status: String?,
)
