package com.im.versechat.ui.dashboard.fragments.messages.fragments.messages.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.im.versechat.R
import com.im.versechat.model.messages.Messages
import kotlinx.android.synthetic.main.friends_item_layout.view.*
import kotlinx.android.synthetic.main.messages_item_layout.view.*
import java.lang.Exception

class MessagesAdapter : RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>() {

    private lateinit var items: List<Messages>

    fun setMessages(items: List<Messages>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        return MessagesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.messages_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MessagesViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {


        fun bind(messages: Messages) {


            itemView.message_item_user_name.text = messages.toId

            getImages(messages.toId.toString(), itemView)


        }


        fun getImages(userID: String, itemView: View) {

            try {
                FirebaseStorage.getInstance().reference.child("profileImage/${userID}").downloadUrl.addOnSuccessListener(
                    OnSuccessListener {
                        Glide.with(itemView.context).load(it).into(itemView.message_item_user_image)
                    }
                ).addOnFailureListener(OnFailureListener { exception ->
                    Log.d("profileImage", "${exception.message}")
                })

            } catch (exception: Exception) {
                exception.printStackTrace()
            }

        }


    }


}