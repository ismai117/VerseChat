package com.im.versechat.data.remote.profile

class ProfileNetworkEntity(
    val name: String?,
    val phone: String?,
    val email: String?,
    val userId: String?,
    val image: String?,
    val status: String?,
){
    constructor(): this(null,null,null,null,null,null,)
}