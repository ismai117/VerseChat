package com.im.versechat.repository

import android.util.Log
import androidx.room.withTransaction
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.im.versechat.data.local.Messages.MessagesDao
import com.im.versechat.data.local.database.VerseDatabase
import com.im.versechat.data.local.friends.FriendsDao
import com.im.versechat.data.local.profile.ProfileDao
import com.im.versechat.data.local.profile.ProfileImageDao
import com.im.versechat.data.local.users.UsersDao
import com.im.versechat.data.local.util.*
import com.im.versechat.data.remote.chat.ChatNetworkEntity
import com.im.versechat.data.remote.chat.ChatResponse
import com.im.versechat.data.remote.messages.MessagesNetworkEntity
import com.im.versechat.data.remote.messages.MessagesResponse
import com.im.versechat.data.remote.friends.FriendsNetworkEntity
import com.im.versechat.data.remote.friends.FriendsResponse
import com.im.versechat.data.remote.profile.ProfileNetworkEntity
import com.im.versechat.data.remote.profile.ProfileResponse
import com.im.versechat.data.remote.user.UsersNetworkEntity
import com.im.versechat.data.remote.user.UsersResponse
import com.im.versechat.data.remote.util.*
import com.im.versechat.model.chat.Chat
import com.im.versechat.model.messages.Messages
import com.im.versechat.model.friends.Friends
import com.im.versechat.model.profile.Profile
import com.im.versechat.model.profile.ProfileImage
import com.im.versechat.model.users.Users
import com.im.versechat.util.Resource
import com.im.versechat.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception

import javax.inject.Inject


class Repository
@Inject
constructor(
    private val fAuth: FirebaseAuth,
    private val fStore: FirebaseFirestore,
    private val fStorage: FirebaseStorage,
    private val usersDao: UsersDao,
    private val profileDao: ProfileDao,
    private val messagesDao: MessagesDao,
    private val profileImageDao: ProfileImageDao,
    private val friendsDao: FriendsDao,
    private val verseDatabase: VerseDatabase,
    private val profileResponseMapper: ProfileResponseMapper,
    private val usersResponseMapper: UsersResponseMapper,
    private val friendsResponseMapper: FriendsResponseMapper,
    private val messagesResponseMapper: MessagesResponseMapper,
    private val chatResponseMapper: ChatResponseMapper,
    private val usersCacheMapper: UsersCacheMapper,
    private val profileCacheMapper: ProfileCacheMapper,
    private val profileImageCacheMapper: ProfileImageCacheMapper,
    private val friendsCacheMapper: FriendsCacheMapper,
    private val messagesCacheMapper: MessagesCacheMapper,
) {

    private val userId = fAuth.currentUser?.uid
    private val profile: CollectionReference = fStore.collection("users")
    private val users: CollectionReference = fStore.collection("users")


    fun Users(): Flow<Resource<List<Users>>> {
        return networkBoundResource(
            query = {
                usersCacheMapper.fromNetworkEntityFlow(usersDao.getUsers())
            },
            fetch = {
                getUsers()
            },
            saveFetchResult = {
                verseDatabase.withTransaction {
                    usersDao.deleteUsers()
                    it?.forEach {
                        usersDao.insert(usersCacheMapper.fromDomainModel(it))
                        Log.d("users", "%")
                    }
                }
            }
        )
    }

    fun Profile(): Flow<Resource<Profile>> {
        return networkBoundResource(
            query = {
                profileCacheMapper.fromNetworkEntityFlow(profileDao.getProfile())
            },
            fetch = {
                Log.d("profileImage", "${getProfileImage()}")
                getProfile()
            },
            saveFetchResult = {
                it?.forEach {
                    if (userId == it.userId) {
                        verseDatabase.withTransaction {
                            profileDao.deleteProfile()
                            profileDao.insert(profileCacheMapper.fromDomainModel(it))
                        }
                    }
                }
            }
        )

    }


    fun ProfileImage(): Flow<Resource<ProfileImage>> {
        return networkBoundResource(
            query = {
                profileImageCacheMapper.fromNetworkEntityFlow(profileImageDao.getProfileImage())
            },
            fetch = {
                ProfileImage(getProfileImage())
            },
            saveFetchResult = {
                verseDatabase.withTransaction {
                    profileImageDao.deleteProfileImage()
                    profileImageDao.insert(profileImageCacheMapper.fromDomainModel(it))
                }
            }
        )

    }


    fun Friends(): Flow<Resource<List<Friends>>> {
        return networkBoundResource(
            query = {
                friendsCacheMapper.fromNetworkEntityFlowList(friendsDao.getFriends())
            },
            fetch = {
                getFriends()
            },
            saveFetchResult = {
                verseDatabase.withTransaction {
                    friendsDao.deleteAllFriends()
                    it?.forEach {
                        friendsDao.insert(friendsCacheMapper.fromDomainModel(it))
                        Log.d("friends", "${it.name}")
                    }
                }
            }
        )

    }

    fun Messages(): Flow<Resource<List<Messages>>> {
        return networkBoundResource(
            query = {
                messagesCacheMapper.fromNetworkEntityFlowList(messagesDao.getMessages())
            },
            fetch = {
                getMessages()
            },
            saveFetchResult = {
                verseDatabase.withTransaction {
                    messagesDao.deleteAllMessages()
                    it?.forEach {
                        messagesDao.insert(messagesCacheMapper.fromDomainModel(it))
                        Log.d("messages", "${it.chat_created_at}")
                    }
                }
            }
        )

    }


    suspend fun getProfileImage(): String? {

        var profileImage: String? = null

        try {

            fStorage.reference.child("profileImage/${userId}").downloadUrl.addOnSuccessListener(
                OnSuccessListener {
                    profileImage = it.toString()
                }
            ).addOnFailureListener(OnFailureListener { exception ->
                Log.d("profileImage", "${exception.message}")
            }).await()

        } catch (exception: Exception) {
            Log.d("profileImage", "${exception.message}")
        }

        return profileImage

    }

    suspend fun getProfile(): List<Profile>? {

        val profileResponse = ProfileResponse()

        try {

            profileResponse.profile =
                profile.get().await().documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject(ProfileNetworkEntity::class.java)
                }

        } catch (exception: Exception) {

            profileResponse.exception = exception

        }

        return profileResponseMapper.mapFromNetworkEntityList(profileResponse.profile)
    }

    suspend fun getFriends(): List<Friends>? {

        val friends: CollectionReference =
            fStore.collection("friends")
                .document(userId.toString())
                .collection("all")

        val friendsResponse = FriendsResponse()

        try {

            friendsResponse.users = friends.get().await().documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject(FriendsNetworkEntity::class.java)
            }

        } catch (exception: Exception) {

            friendsResponse.exception = exception

        }

        return friendsResponseMapper.mapFromNetworkEntityList(friendsResponse.users)

    }

    suspend fun getUsers(): List<Users>? {

        val userResponse = UsersResponse()


        try {

            userResponse.users = users.get().await().documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject(UsersNetworkEntity::class.java)
            }

        } catch (exception: Exception) {

            userResponse.exception = exception

        }


        return usersResponseMapper.mapFromNetworkEntityList(userResponse.users)
    }

    suspend fun getMessages(): List<Messages>? {

        val messages: CollectionReference =
            fStore.collection("chat_created")
                .document(userId.toString())
                .collection("chats")

        val messagesResponse = MessagesResponse()

        try {

            messagesResponse.messages =
                messages.get().await().documents.mapNotNull { documentSnapshot ->

                    documentSnapshot.toObject(MessagesNetworkEntity::class.java)

                }

        } catch (exception: Exception) {

            messagesResponse.exception = exception

        }

        return messagesResponseMapper.mapFromNetworkEntityList(messagesResponse.messages)

    }

    suspend fun getChat(currentUser: String, friendId: String): List<Chat>? {

        val chat: CollectionReference =
            fStore.collection("chat")
                .document(currentUser.toString())
                .collection("chats")
                .document(friendId.toString())
                .collection("messages")

        val chatResponse = ChatResponse()

        try {

            chatResponse.chats = chat.get().await().documents.mapNotNull { documentSnapshot ->

                documentSnapshot.toObject(ChatNetworkEntity::class.java)

            }


            chatResponse.chats!!.forEach {
                Log.d("chatMessages", "${it.message}")
            }


        } catch (exception: Exception) {

            chatResponse.exception = exception
        }

        return chatResponseMapper.mapFromNetworkEntityList(chatResponse.chats)

    }


}