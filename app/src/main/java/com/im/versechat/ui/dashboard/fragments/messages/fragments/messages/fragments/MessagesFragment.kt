package com.im.versechat.ui.dashboard.fragments.messages.fragments.messages.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.im.versechat.R
import com.im.versechat.databinding.FragmentMessagesBinding
import com.im.versechat.ui.dashboard.fragments.messages.adapters.FriendsAdapter
import com.im.versechat.ui.dashboard.fragments.messages.fragments.messages.adapters.MessagesAdapter
import com.im.versechat.ui.dashboard.fragments.messages.fragments.messages.viewmodels.MessagesViewModel
import com.im.versechat.ui.dashboard.fragments.messages.viewmodels.FriendsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MessagesFragment
@Inject
constructor(
    private val fAuth: FirebaseAuth,
    private val fStore: FirebaseFirestore,
) : Fragment() {


    private var messagesBinding: FragmentMessagesBinding? = null
    private val binding get() = messagesBinding!!
    private val friendsModel: FriendsViewModel by viewModels()
    private val messagesModel: MessagesViewModel by viewModels()
    private lateinit var friendsAdapter: FriendsAdapter
    private lateinit var messagesAdapter: MessagesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        friendsAdapter = FriendsAdapter()
        messagesAdapter = MessagesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        messagesBinding = FragmentMessagesBinding.inflate(inflater, container, false)
        val view = binding.root




        friendsModel.friends.observe(this.viewLifecycleOwner, { result ->

            result?.let {


                if (result.data != null) {
                    initfriendsRecycler()
                    friendsAdapter.setFriends(result.data)
                    binding.friendsProgressBar.visibility = View.GONE
                } else {
                    binding.friendsProgressBar.visibility = View.GONE
                }


            }

        })



        messagesModel.messages.observe(this.viewLifecycleOwner, { result ->

            result?.let {


                if (result.data != null) {
                    initMessagesRecycler()
                    messagesAdapter.setMessages(result.data)
                    binding.friendsProgressBar.visibility = View.GONE
                } else {
                    binding.friendsProgressBar.visibility = View.GONE
                }


            }


        })


        binding.searchIcon.setOnClickListener { findNavController().navigate(R.id.action_messagesFragment_to_usersFragment) }

        return view
    }

    private fun initfriendsRecycler() {
        binding.friendsRecyclerHorView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.friendsRecyclerHorView.setHasFixedSize(true)
        binding.friendsRecyclerHorView.adapter = friendsAdapter
    }

    private fun initMessagesRecycler() {
        binding.messagesRecyclerHorView.layoutManager = LinearLayoutManager(requireContext())
        binding.messagesRecyclerHorView.setHasFixedSize(true)
        binding.messagesRecyclerHorView.adapter = messagesAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        messagesBinding = null
    }

}