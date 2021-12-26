package com.im.versechat.data.remote.messages

import java.lang.Exception

data class MessagesResponse (
    var messages: List<MessagesNetworkEntity>? = null,
    var exception: Exception? = null
){
    constructor(): this(null, null)
}