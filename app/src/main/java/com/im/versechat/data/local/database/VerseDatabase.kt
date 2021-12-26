package com.im.versechat.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.im.versechat.data.local.Messages.MessagesCacheEntity
import com.im.versechat.data.local.Messages.MessagesDao
import com.im.versechat.data.local.friends.FriendsCacheEntity
import com.im.versechat.data.local.friends.FriendsDao
import com.im.versechat.data.local.profile.ProfileCacheEntity
import com.im.versechat.data.local.profile.ProfileDao
import com.im.versechat.data.local.profile.ProfileImageCacheEntity
import com.im.versechat.data.local.profile.ProfileImageDao
import com.im.versechat.data.local.users.UsersCacheEntity
import com.im.versechat.data.local.users.UsersDao

@Database(
    entities = [UsersCacheEntity::class, ProfileCacheEntity::class, ProfileImageCacheEntity::class, FriendsCacheEntity::class, MessagesCacheEntity::class],
    version = 9,
    exportSchema = false
)
abstract class VerseDatabase  : RoomDatabase(){

    abstract fun getUsers(): UsersDao
    abstract fun getProfile(): ProfileDao
    abstract fun getProfileImage(): ProfileImageDao
    abstract fun getFriends(): FriendsDao
    abstract fun getMessages(): MessagesDao


}