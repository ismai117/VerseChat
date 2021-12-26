package com.im.versechat.data.local.util

import com.im.versechat.data.local.friends.FriendsCacheEntity
import com.im.versechat.data.local.profile.ProfileCacheEntity
import com.im.versechat.data.local.profile.ProfileImageCacheEntity
import com.im.versechat.model.friends.Friends
import com.im.versechat.model.profile.Profile
import com.im.versechat.model.profile.ProfileImage
import com.im.versechat.model.util.EntityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileImageCacheMapper : EntityMapper<ProfileImageCacheEntity?, ProfileImage> {


    override fun toDomainModel(entity: ProfileImageCacheEntity?): ProfileImage {
        return ProfileImage(
            profileImage = entity?.profileImage
        )
    }

    override fun fromDomainModel(entity: ProfileImage): ProfileImageCacheEntity {
        return ProfileImageCacheEntity(
            id = 0,
            profileImage = entity.profileImage
        )
    }

    fun fromNetworkEntityFlow(networkEntity: Flow<ProfileImageCacheEntity>): Flow<ProfileImage> {
        return networkEntity.map { toDomainModel(it) }
    }


}