package com.im.versechat.data.remote.friends

import java.lang.Exception

data class FriendsResponse (
    var users: List<FriendsNetworkEntity>? = null,
    var exception: Exception? = null
){
    constructor(): this(null, null)
}