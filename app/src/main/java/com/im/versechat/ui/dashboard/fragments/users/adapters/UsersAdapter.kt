package com.im.versechat.ui.dashboard.fragments.users.adapters

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
import com.im.versechat.model.users.Users
import com.im.versechat.ui.dashboard.fragments.users.AddFriendsInterface
import kotlinx.android.synthetic.main.friends_item_layout.view.*
import kotlinx.android.synthetic.main.users_item_layout.view.*

class UsersAdapter(val addFriendsInterface: AddFriendsInterface) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>(){

    private lateinit var items: List<Users>

    fun setUsers(items: List<Users>){
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.users_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        return holder.bind(items[position], addFriendsInterface)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class UsersViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview){

        fun bind(users: Users, addFriendsInterface: AddFriendsInterface) {

            itemView.users_item_name.text = users.name

            getImages(users.userId.toString(), itemView)

            val position = adapterPosition

            itemView.add_friend.setOnClickListener {

                if (position == adapterPosition){


                    addFriendsInterface.addFriends(users)


                }

            }

        }

        fun getImages(userID: String, itemView: View){

            FirebaseStorage.getInstance().reference.child("profileImage/${userID}").downloadUrl.addOnSuccessListener(
                OnSuccessListener {
                    Glide.with(itemView.context).load(it).into(itemView.users_item_image)
                }
            ).addOnFailureListener(OnFailureListener { exception ->
                Log.d("profileImage", "${exception.message}")
            })

        }


    }

}