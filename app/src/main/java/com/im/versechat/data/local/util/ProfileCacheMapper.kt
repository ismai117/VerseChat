package com.im.versechat.data.local.util

import com.im.versechat.data.local.profile.ProfileCacheEntity
import com.im.versechat.model.profile.Profile
import com.im.versechat.model.util.EntityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileCacheMapper : EntityMapper<ProfileCacheEntity?, Profile> {

    override fun toDomainModel(entity: ProfileCacheEntity?): Profile {
        return Profile(
            name = entity?.name,
            phone= entity?.phone,
            email= entity?.email ,
            userId = entity?.userId,
            image= entity?.image,
            status= entity?.status,
        )
    }

    override fun fromDomainModel(entity: Profile): ProfileCacheEntity {
        return ProfileCacheEntity(
            id = 0,
            name = entity.name,
            phone= entity.phone,
            email= entity.email ,
            userId = entity.userId,
            image= entity.image,
            status= entity.status,
        )
    }


    fun fromNetworkEntityList(networkEntity: List<ProfileCacheEntity>): List<Profile> {
        return networkEntity.map { toDomainModel(it) }
    }

    fun mapToEntityList(entity: List<Profile>): List<ProfileCacheEntity> {
        return entity.map { fromDomainModel(it) }
    }


    fun fromNetworkEntityFlow(networkEntity: Flow<ProfileCacheEntity>): Flow<Profile>{
        return networkEntity.map { toDomainModel(it) }
    }

}