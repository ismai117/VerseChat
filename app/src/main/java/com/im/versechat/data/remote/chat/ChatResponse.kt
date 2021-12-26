package com.im.versechat.data.remote.chat

import java.lang.Exception

data class ChatResponse(
    var chats: List<ChatNetworkEntity>? = null,
    var exception: Exception? = null
){
    constructor(): this(null, null)
}