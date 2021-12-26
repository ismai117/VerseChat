package com.im.versechat.data.local.util

import com.im.versechat.data.local.Messages.MessagesCacheEntity
import com.im.versechat.data.local.friends.FriendsCacheEntity
import com.im.versechat.data.remote.messages.MessagesNetworkEntity
import com.im.versechat.model.friends.Friends
import com.im.versechat.model.messages.Messages
import com.im.versechat.model.util.EntityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MessagesCacheMapper : EntityMapper<MessagesCacheEntity?, Messages> {

    override fun toDomainModel(entity: MessagesCacheEntity?): Messages {
        return Messages(
            chat_created_at = entity?.chat_created_at,
            toId = entity?.toId,
            fromId = entity?.fromId
        )
    }

    override fun fromDomainModel(entity: Messages): MessagesCacheEntity {
        return MessagesCacheEntity(
            id = 0,
            chat_created_at = entity.chat_created_at,
            toId = entity.toId,
            fromId = entity.fromId
        )
    }


    fun fromNetworkEntityList(networkEntity: List<MessagesCacheEntity>): List<Messages> {
        return networkEntity.map { toDomainModel(it) }
    }

    fun mapToEntityList(entity: List<Messages>): List<MessagesCacheEntity> {
        return entity.map { fromDomainModel(it) }
    }


    fun fromNetworkEntityFlowList(networkEntity: Flow<List<MessagesCacheEntity>>): Flow<List<Messages>> {
        return networkEntity.map { fromNetworkEntityList(it) }
    }

}