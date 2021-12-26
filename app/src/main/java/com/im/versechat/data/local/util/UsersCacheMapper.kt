package com.im.versechat.data.local.util


import com.im.versechat.data.local.users.UsersCacheEntity

import com.im.versechat.model.users.Users
import com.im.versechat.model.util.EntityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UsersCacheMapper : EntityMapper<UsersCacheEntity?, Users> {

    override fun toDomainModel(entity: UsersCacheEntity?): Users {
        return Users(
            name = entity?.name,
            phone = entity?.phone,
            email = entity?.email,
            userId = entity?.userId,
            image = entity?.image,
            status = entity?.status,
        )
    }

    override fun fromDomainModel(entity: Users): UsersCacheEntity {
        return UsersCacheEntity(
            id = 0,
            name = entity.name,
            phone = entity.phone,
            email = entity.email,
            userId = entity.userId,
            image = entity.image,
            status = entity.status,
        )
    }


    fun fromNetworkEntityList(networkEntity: List<UsersCacheEntity>): List<Users> {
        return networkEntity.map { toDomainModel(it) }
    }

    fun mapToEntityList(entity: List<Users>): List<UsersCacheEntity> {
        return entity.map { fromDomainModel(it) }
    }


    fun fromNetworkEntityFlow(networkEntity: Flow<List<UsersCacheEntity>>): Flow<List<Users>> {
        return networkEntity.map { fromNetworkEntityList(it) }
    }


}