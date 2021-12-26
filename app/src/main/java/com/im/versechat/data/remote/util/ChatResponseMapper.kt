package com.im.versechat.data.remote.util

import com.im.versechat.data.remote.chat.ChatNetworkEntity
import com.im.versechat.data.remote.messages.MessagesNetworkEntity
import com.im.versechat.model.chat.Chat
import com.im.versechat.model.messages.Messages
import com.im.versechat.model.util.EntityMapper

class ChatResponseMapper : EntityMapper<ChatNetworkEntity, Chat>{

    override fun toDomainModel(entity: ChatNetworkEntity): Chat {
        return Chat(
            message = entity.message,
            toId = entity.toId,
            fromId = entity.fromId
        )
    }

    override fun fromDomainModel(entity: Chat): ChatNetworkEntity {
        TODO("Not yet implemented")
    }

    fun mapFromNetworkEntityList(entityList: List<ChatNetworkEntity>?): List<Chat>? {
        return entityList?.map { toDomainModel(it) }
    }


}