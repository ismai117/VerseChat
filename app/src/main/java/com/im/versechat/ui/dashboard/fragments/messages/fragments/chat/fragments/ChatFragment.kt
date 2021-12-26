package com.im.versechat.ui.dashboard.fragments.messages.fragments.chat.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.im.versechat.R
import com.im.versechat.databinding.FragmentChatBinding
import com.im.versechat.ui.dashboard.fragments.messages.fragments.chat.adapters.ChatAdapter
import com.im.versechat.ui.dashboard.fragments.messages.fragments.chat.viewmodels.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint

import java.text.SimpleDateFormat
import java.util.*

import javax.inject.Inject


@AndroidEntryPoint
class ChatFragment
@Inject
constructor(
    private val fAuth: FirebaseAuth,
    private val fStore: FirebaseFirestore,
) : Fragment() {

    private var chatBinding: FragmentChatBinding? = null
    private val binding get() = chatBinding!!
    private val chatsModel: ChatViewModel by viewModels()
    private var currentUser: String? = null
    private var friendId: String? = null
    private lateinit var chatAdapter: ChatAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatAdapter = ChatAdapter()

        currentUser = fAuth.currentUser?.uid

        arguments?.let {

            val friend = ChatFragmentArgs.fromBundle(
                it).friends

            if (friend != null) {


                (activity as AppCompatActivity).supportActionBar?.title = "${friend.name}"

                friendId = friend.userId

            }

        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        chatBinding = FragmentChatBinding.inflate(inflater, container, false)
        val view = binding.root








        chatsModel.chat(currentUser.toString(), friendId.toString()).observe(this.viewLifecycleOwner, { result ->


            result?.let {



                if (result != null){

                    initChatRecycler()
                    chatAdapter.setChat(it)
                    binding.chatProgressBar.visibility = View.GONE
                }else{
                    Log.d("chatMessages", "error")
                    binding.chatProgressBar.visibility = View.GONE
                }
            }


        })





        binding.sendMessage.setOnClickListener {

            val message = binding.messageInput.text.toString()

            saveMessage(message)

            binding.messageInput.text.clear()

        }


        return view
    }


   private fun initChatRecycler(){
       binding.chatRecyclerView.layoutManager = LinearLayoutManager(requireContext())
       binding.chatRecyclerView.setHasFixedSize(true)
       binding.chatRecyclerView.adapter = chatAdapter
   }



    private fun saveMessage(message: String) {


        when {

            TextUtils.isEmpty(message) -> {
                binding.messageInput.error = "Can't send empty message"
            }

            else -> {


                saveMessageFromUser(message)

                saveMessageToUser(message)


            }

        }


    }

    private fun saveMessageToUser(message: String) {

        val sentMessage = hashMapOf(
            "message" to message,
            "toId" to friendId,
            "fromId" to currentUser
        )

        fStore.collection("chat")
            .document(friendId.toString())
            .collection("chats")
            .document(currentUser.toString())
            .collection("messages")
            .document()
            .set(sentMessage)
            .addOnSuccessListener(
                OnSuccessListener {
                    Log.d("chat", "success: message sent")
                    Toast.makeText(requireContext(), "Message Sent", Toast.LENGTH_LONG).show()
                }
            )
            .addOnFailureListener(
                OnFailureListener { e ->
                    Log.d("chat", "${e.message}")
                }
            )

        val sf = SimpleDateFormat("dd/MM/yyyy")
        val date = sf.format(Date())

        val chat = hashMapOf(
            "chat_created_at" to date.toString(),
            "toId" to currentUser,
            "fromId" to friendId
        )


        fStore.collection("chat_created")
            .document(friendId.toString())
            .collection("chats")
            .document(currentUser.toString())
            .set(chat).addOnSuccessListener(
                OnSuccessListener {
                    Log.d("chat_created", "success: chat created")
                }
            )
            .addOnFailureListener(
                OnFailureListener { e ->
                    Log.d("chat", "${e.message}")
                }
            )


    }

    private fun saveMessageFromUser(message: String) {

        val sentMessage = hashMapOf(
            "message" to message,
            "toId" to friendId,
            "fromId" to currentUser
        )

        fStore.collection("chat")
            .document(currentUser.toString())
            .collection("chats")
            .document(friendId.toString())
            .collection("messages")
            .document()
            .set(sentMessage)
            .addOnSuccessListener(
                OnSuccessListener {
                    Log.d("chat", "success: message sent")
                }
            )
            .addOnFailureListener(
                OnFailureListener { e ->
                    Log.d("chat", "${e.message}")
                }
            )



        val sf = SimpleDateFormat("dd/MM/yyyy")
        val date = sf.format(Date())

        val chat = hashMapOf(
            "chat_created_at" to date.toString(),
            "toId" to friendId,
            "fromId" to currentUser
        )

        fStore.collection("chat_created")
            .document(currentUser.toString())
            .collection("chats")
            .document(friendId.toString())
            .set(chat).addOnSuccessListener(
                OnSuccessListener {
                    Log.d("chat_created", "success: chat created")
                }
            )
            .addOnFailureListener(
                OnFailureListener { e ->
                    Log.d("chat", "${e.message}")
                }
            )


    }








}