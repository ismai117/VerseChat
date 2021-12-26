package com.im.versechat.data.remote.util

import com.im.versechat.data.remote.messages.MessagesNetworkEntity
import com.im.versechat.model.messages.Messages
import com.im.versechat.model.util.EntityMapper

class MessagesResponseMapper : EntityMapper<MessagesNetworkEntity, Messages> {

    override fun toDomainModel(entity: MessagesNetworkEntity): Messages {
        return Messages(
           chat_created_at = entity.chat_created_at,
            toId = entity.toId,
            fromId = entity.fromId
        )
    }

    override fun fromDomainModel(entity: Messages): MessagesNetworkEntity {
        TODO("Not yet implemented")
    }


    fun mapFromNetworkEntityList(entityList: List<MessagesNetworkEntity>?): List<Messages>? {
        return entityList?.map { toDomainModel(it) }
    }

}