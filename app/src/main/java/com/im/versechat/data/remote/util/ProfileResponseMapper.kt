package com.im.versechat.data.remote.util

import com.im.versechat.data.remote.profile.ProfileNetworkEntity
import com.im.versechat.model.profile.Profile
import com.im.versechat.model.util.EntityMapper

class ProfileResponseMapper : EntityMapper<ProfileNetworkEntity, Profile> {

    override fun toDomainModel(entity: ProfileNetworkEntity): Profile {
        return Profile(
            name = entity.name,
            phone= entity.phone,
            email= entity.email ,
            userId = entity.userId,
            image= entity.image,
            status= entity.status,
        )
    }

    override fun fromDomainModel(entity: Profile): ProfileNetworkEntity {
        TODO("Not yet implemented")
    }

    fun mapFromNetworkEntityList(entityList: List<ProfileNetworkEntity>?): List<Profile>?{
        return entityList?.map { toDomainModel(it) }
    }

}