package com.im.versechat.data.remote.user

import java.lang.Exception

data class UsersResponse (
    var users: List<UsersNetworkEntity>? = null,
    var exception: Exception? = null
    ){
    constructor(): this(null, null)
}