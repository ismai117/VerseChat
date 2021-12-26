package com.im.versechat.data.remote.user

data class UsersNetworkEntity(
    val name: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val userId: String? = null,
    val image: String? = null,
    val status: String? = null
){
    constructor(): this(null,null,null,null,null,null,)
}
