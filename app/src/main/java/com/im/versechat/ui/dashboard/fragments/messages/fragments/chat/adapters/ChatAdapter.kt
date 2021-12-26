package com.im.versechat.ui.dashboard.fragments.messages.fragments.chat.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.im.versechat.R
import com.im.versechat.model.chat.Chat
import kotlinx.android.synthetic.main.message_item_left_layout.view.*
import kotlinx.android.synthetic.main.message_item_right_layout.view.*
import java.lang.Exception

class ChatAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var items: List<Chat>

    fun setChat(items: List<Chat>) {
        this.items = items
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return items.size
    }


    override fun getItemViewType(position: Int): Int {

        val chat = items.get(position)

        if (FirebaseAuth.getInstance().currentUser?.uid.toString().equals(chat.fromId)) {
            return VIEW_TYPE_MESSAGE_SENT
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {


            VIEW_TYPE_MESSAGE_SENT -> {
                SendMessageViewHolder(inflater.inflate(R.layout.message_item_left_layout,
                    parent,
                    false))
            }

            VIEW_TYPE_MESSAGE_RECEIVED -> {
                ReceiveMessageViewHolder(inflater.inflate(R.layout.message_item_right_layout,
                    parent,
                    false))
            }

            else -> {
                throw IllegalArgumentException("Unsupported layout")
            }

        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val chat: Chat = items.get(position)



        when (holder.itemViewType) {


            VIEW_TYPE_MESSAGE_SENT -> {

                try {
                    (holder as SendMessageViewHolder).bind(chat)
                } catch (exception: Exception) {
                    exception.printStackTrace()

                }

            }

            VIEW_TYPE_MESSAGE_RECEIVED -> {


                try {
                    (holder as ReceiveMessageViewHolder).bind(chat)
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }


            }

        }

    }


    class SendMessageViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {


        fun bind(chat: Chat) {

            itemView.currentUser_message.text = chat.message

        }


    }

    class ReceiveMessageViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {


        fun bind(chat: Chat) {

            itemView.friend_message.text = chat.message
        }

    }

    companion object {
        private const val VIEW_TYPE_MESSAGE_SENT = 1
        private const val VIEW_TYPE_MESSAGE_RECEIVED = 2
    }


}


