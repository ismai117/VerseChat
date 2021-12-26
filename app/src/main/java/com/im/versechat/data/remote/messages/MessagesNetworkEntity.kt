package com.im.versechat.data.remote.messages

data class MessagesNetworkEntity(

    val chat_created_at: String? = null,
    val toId: String? = null,
    val fromId: String? = null,

) {
    constructor() : this(null, null, null)
}
