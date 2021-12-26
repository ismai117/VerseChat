package com.im.versechat.di

import android.content.Context
import androidx.room.Room
import com.im.versechat.data.local.Messages.MessagesDao
import com.im.versechat.data.local.database.VerseDatabase
import com.im.versechat.data.local.friends.FriendsDao
import com.im.versechat.data.local.profile.ProfileDao
import com.im.versechat.data.local.profile.ProfileImageDao
import com.im.versechat.data.local.users.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Singleton
    @Provides
    fun provideVerseDatabase(@ApplicationContext context: Context): VerseDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            VerseDatabase::class.java,
            "profile"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideUsersDao(verseDatabase: VerseDatabase): UsersDao {
        return verseDatabase.getUsers()
    }

    @Singleton
    @Provides
    fun provideProfileDao(verseDatabase: VerseDatabase): ProfileDao {
        return verseDatabase.getProfile()
    }

    @Singleton
    @Provides
    fun provideProfileImageDao(verseDatabase: VerseDatabase): ProfileImageDao {
        return verseDatabase.getProfileImage()
    }

    @Singleton
    @Provides
    fun provideFriendsDao(verseDatabase: VerseDatabase): FriendsDao {
        return verseDatabase.getFriends()
    }

    @Singleton
    @Provides
    fun provideMessagesDao(verseDatabase: VerseDatabase): MessagesDao {
        return verseDatabase.getMessages()
    }

}