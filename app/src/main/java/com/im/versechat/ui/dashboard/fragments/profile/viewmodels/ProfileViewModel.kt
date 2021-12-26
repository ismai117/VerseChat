package com.im.versechat.ui.dashboard.fragments.profile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.im.versechat.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val repository: Repository,
) : ViewModel() {


    val profile = repository.Profile().asLiveData()

    val profileImage = repository.ProfileImage().asLiveData()

}