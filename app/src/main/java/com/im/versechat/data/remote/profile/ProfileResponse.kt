package com.im.versechat.data.remote.profile

import java.lang.Exception

data class ProfileResponse(
    var profile: List<ProfileNetworkEntity>? = null,
    var exception: Exception? = null,
) {
    constructor() : this(null, null)
}