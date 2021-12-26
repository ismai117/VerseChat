package com.im.versechat.model.users

import java.io.Serializable

data class Users(
    val name: String?,
    val phone: String?,
    val email: String?,
    val userId: String?,
    val image: String?,
    val status: String?
): Serializable
