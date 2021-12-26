package com.im.versechat.model.friends

import java.io.Serializable

data class Friends(
    val name: String?,
    val phone: String?,
    val email: String?,
    val userId: String?,
    val image: String?,
    val status: String?
): Serializable
