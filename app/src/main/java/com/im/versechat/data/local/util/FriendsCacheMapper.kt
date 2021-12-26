package com.im.versechat.data.local.util

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.im.versechat.data.local.friends.FriendsCacheEntity
import com.im.versechat.data.local.profile.ProfileCacheEntity
import com.im.versechat.model.friends.Friends
import com.im.versechat.model.profile.Profile
import com.im.versechat.model.util.EntityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class FriendsCacheMapper : EntityMapper<FriendsCacheEntity?, Friends> {

    override fun toDomainModel(entity: FriendsCacheEntity?): Friends {
        return Friends(
            name = entity?.name,
            phone = entity?.phone,
            email = entity?.email,
            userId = entity?.userId,
            image = entity?.image,
            status = entity?.status,
        )
    }

    override fun fromDomainModel(entity: Friends): FriendsCacheEntity {
        return FriendsCacheEntity(
            id = 0,
            name = entity.name,
            phone = entity.phone,
            email = entity.email,
            userId = entity.userId,
            image = entity.image,
            status = entity.status,
        )
    }


    fun fromNetworkEntityList(networkEntity: List<FriendsCacheEntity>): List<Friends> {
        return networkEntity.map { toDomainModel(it) }
    }

    fun mapToEntityList(entity: List<Friends>): List<FriendsCacheEntity> {
        return entity.map { fromDomainModel(it) }
    }


    fun fromNetworkEntityFlowList(networkEntity: Flow<List<FriendsCacheEntity>>): Flow<List<Friends>> {
        return networkEntity.map { fromNetworkEntityList(it) }
    }

}
