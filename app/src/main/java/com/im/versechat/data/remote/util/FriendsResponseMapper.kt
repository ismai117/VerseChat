package com.im.versechat.data.remote.util

import com.im.versechat.data.remote.friends.FriendsNetworkEntity
import com.im.versechat.model.friends.Friends
import com.im.versechat.model.util.EntityMapper

class FriendsResponseMapper : EntityMapper<FriendsNetworkEntity, Friends>{

    override fun toDomainModel(entity: FriendsNetworkEntity): Friends {
        return Friends(
            name = entity.name,
            phone= entity.phone,
            email= entity.email ,
            userId = entity.userId,
            image= entity.image,
            status= entity.status,
        )
    }

    override fun fromDomainModel(entity: Friends): FriendsNetworkEntity {
        TODO("Not yet implemented")
    }


    fun mapFromNetworkEntityList(entityList: List<FriendsNetworkEntity>?): List<Friends>?{
        return entityList?.map { toDomainModel(it) }
    }

}