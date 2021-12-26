package com.im.versechat.ui.dashboard.fragments.messages.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.im.versechat.R
import com.im.versechat.model.friends.Friends
import com.im.versechat.ui.dashboard.fragments.messages.fragments.messages.fragments.MessagesFragmentDirections
import kotlinx.android.synthetic.main.friends_item_layout.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.CoroutineContext

class FriendsAdapter() : RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {

    private lateinit var items: List<Friends>

    fun setFriends(items: List<Friends>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        return FriendsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.friends_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class FriendsViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {


        fun bind(friends: Friends) {


            itemView.friends_name.text = friends.name
            Glide.with(itemView.context).load(friends.image).into(itemView.friends_image)

            val position = adapterPosition


            getImages(friends.userId.toString(), itemView)

            itemView.setOnClickListener {

                val action = MessagesFragmentDirections.actionMessagesFragmentToChatFragment()

                action.friends = friends

                Navigation.findNavController(it).navigate(action)

            }

        }

        fun getImages(userID: String, itemView: View) {


            try {

                FirebaseStorage.getInstance().reference.child("profileImage/${userID}").downloadUrl.addOnSuccessListener(
                    OnSuccessListener {
                        Glide.with(itemView.context).load(it).into(itemView.friends_image)
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