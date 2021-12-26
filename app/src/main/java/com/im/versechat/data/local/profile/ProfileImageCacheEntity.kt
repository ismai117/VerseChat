package com.im.versechat.data.local.profile

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profileImage")
data class ProfileImageCacheEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val profileImage: String?
)
