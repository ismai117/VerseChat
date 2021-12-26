package com.im.versechat.ui.dashboard.fragments.messages.fragments.chat.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.im.versechat.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ChatViewModel
@Inject
constructor(
    private val repository: Repository,
): ViewModel(){


    fun chat(currentUser: String, friendId: String) = liveData(Dispatchers.IO) {
        emit(repository.getChat(currentUser, friendId))
    }

}