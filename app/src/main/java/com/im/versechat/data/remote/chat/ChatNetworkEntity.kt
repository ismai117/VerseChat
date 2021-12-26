package com.im.versechat.data.remote.chat

data class ChatNetworkEntity(
    val message: String? = null,
    val toId: String? = null,
    val fromId: String? = null,
) {
    constructor() : this(null, null, null)
}
