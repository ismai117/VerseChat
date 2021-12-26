package com.im.versechat.data.local.profile

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileCacheEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String?,
    val phone: String?,
    val email: String?,
    val userId: String?,
    val image: String?,
    val status: String?,
)
