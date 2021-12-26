package com.im.versechat.ui.dashboard.fragments.users.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.im.versechat.R
import com.im.versechat.databinding.FragmentMessagesBinding
import com.im.versechat.databinding.FragmentUsersBinding
import com.im.versechat.model.users.Users
import com.im.versechat.ui.dashboard.fragments.users.AddFriendsInterface
import com.im.versechat.ui.dashboard.fragments.users.adapters.UsersAdapter
import com.im.versechat.ui.dashboard.fragments.users.viewmodels.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UsersFragment
@Inject
constructor(
    private val fAuth: FirebaseAuth,
    private val fStore: FirebaseFirestore,
) : Fragment(), AddFriendsInterface {

    private var usersBinding: FragmentUsersBinding? = null
    private val binding get() = usersBinding!!
    private val usersModel: UsersViewModel by viewModels()
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usersAdapter = UsersAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        usersBinding = FragmentUsersBinding.inflate(inflater, container, false)
        val view = binding.root


        val userId = fAuth.currentUser?.uid

        usersModel.users.observe(this.viewLifecycleOwner, { result ->

            result?.let {


                if (result.data != null) {
                    initRecycler()
                    usersAdapter.setUsers(result.data)
                    binding.usersProgressBar.visibility = View.GONE
                }else{
                    binding.usersProgressBar.visibility = View.GONE
                }

            }

        })


        return view
    }

    private fun initRecycler() {
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.usersRecyclerView.setHasFixedSize(true)
        binding.usersRecyclerView.adapter = usersAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        usersBinding = null
    }

    override fun addFriends(users: Users) {


        val userId = fAuth.currentUser?.uid

        fStore.collection("friends").document(userId.toString()).collection("all")
            .document(users.userId.toString())
            .set(users)
            .addOnSuccessListener(
                OnSuccessListener { documentSnapshot ->
                    Toast.makeText(requireContext(), "Added Friend", Toast.LENGTH_LONG).show()

                    Log.d("add_friend", "Success")
                }
            )
            .addOnFailureListener(
                OnFailureListener { e ->
                    Log.d("add_friend", "Success: ${e.message}")
                }
            )


    }

}