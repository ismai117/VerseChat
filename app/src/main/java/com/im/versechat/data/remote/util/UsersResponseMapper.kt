package com.im.versechat.data.remote.util

import com.im.versechat.data.remote.user.UsersNetworkEntity
import com.im.versechat.model.users.Users
import com.im.versechat.model.util.EntityMapper

class UsersResponseMapper : EntityMapper<UsersNetworkEntity, Users>{

    override fun toDomainModel(entity: UsersNetworkEntity): Users {
        return Users(
            name = entity.name,
          phone= entity.phone,
          email= entity.email ,
          userId = entity.userId,
          image= entity.image,
          status= entity.status,
        )
    }

    override fun fromDomainModel(entity: Users): UsersNetworkEntity {
        TODO("Not yet implemented")
    }


    fun mapFromNetworkEntityList(entityList: List<UsersNetworkEntity>?): List<Users>?{
        return entityList?.map { toDomainModel(it) }
    }

}