package com.im.versechat.di

import com.im.versechat.data.local.util.*
import com.im.versechat.data.remote.util.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object InteractorsModule {


    @Singleton
    @Provides
    fun provideUsersResponseMapper(): UsersResponseMapper {
        return UsersResponseMapper()
    }

    @Singleton
    @Provides
    fun provideProfileResponseMapper(): ProfileResponseMapper {
        return ProfileResponseMapper()
    }

    @Singleton
    @Provides
    fun provideFriendsResponseMapper(): FriendsResponseMapper {
        return FriendsResponseMapper()
    }

    @Singleton
    @Provides
    fun provideMessagesResponseMapper(): MessagesResponseMapper {
        return MessagesResponseMapper()
    }

    @Singleton
    @Provides
    fun provideChatResponseMapper(): ChatResponseMapper{
        return ChatResponseMapper()
    }

    @Singleton
    @Provides
    fun provideUsersCacheMapper(): UsersCacheMapper {
        return UsersCacheMapper()
    }

    @Singleton
    @Provides
    fun provideProfileCacheMapper(): ProfileCacheMapper {
        return ProfileCacheMapper()
    }

    @Singleton
    @Provides
    fun provideProfileImageCacheMapper(): ProfileImageCacheMapper{
        return ProfileImageCacheMapper()
    }

    @Singleton
    @Provides
    fun provideFriendsCacheMapper(): FriendsCacheMapper {
        return FriendsCacheMapper()
    }

    @Singleton
    @Provides
    fun provideMessagesCacheMapper(): MessagesCacheMapper {
        return MessagesCacheMapper()
    }



}