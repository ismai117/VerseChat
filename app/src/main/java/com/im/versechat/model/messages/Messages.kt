package com.im.versechat.model.messages

import java.io.Serializable

data class Messages(
    var chat_created_at: String?,
    var toId: String?,
    var fromId: String?,
): Serializable
