package com.im.versechat.ui.dashboard.fragments.profile.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.UploadTask
import com.im.versechat.databinding.FragmentProfileBinding
import com.im.versechat.ui.dashboard.fragments.profile.viewmodels.ProfileViewModel
import com.im.versechat.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment
@Inject
constructor(
    private val fAuth: FirebaseAuth,
    private val fStore: FirebaseFirestore,
    private val fSorage: FirebaseStorage,
) : Fragment() {


    private var profileBinding: FragmentProfileBinding? = null
    private val binding get() = profileBinding!!
    private val profileModel: ProfileViewModel by viewModels()
    private var userId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userId = fAuth.currentUser?.uid

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        profileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root



        profileModel.profile.observe(this.viewLifecycleOwner, { result ->

            result?.let {


                if (it.data != null) {



                    binding.profileName.text = "${it.data.name}"


                    binding.profileProgressBar.isVisible =
                        result is Resource.Loading && result.data == null

                } else {
                    Log.d("newsnba", "error")
                }


            }


        })


        profileModel.profileImage.observe(this.viewLifecycleOwner, { result ->

            result?.let {

                if (it.data != null) {

                    Glide.with(requireContext()).load(result.data?.profileImage).into(binding.profileImage)

                    binding.profileProgressBar.isVisible =
                        result is Resource.Loading && result.data == null

                } else {
                    Log.d("profileImage", "error")
                }


            }


        })




        binding.addImage.setOnClickListener {
           addImage()
        }

        return view

    }

    private fun addImage() {

        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, 100)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {


            binding.profileImage.setImageURI(data?.data)
            saveImage(data?.data)

            binding.profileProgressBar.visibility = View.VISIBLE

        }
    }


    private fun saveImage(data: Uri?) {

        if (data != null) {

            val storageRef = fSorage.reference.child("profileImage/${userId}")

            storageRef.putFile(data)
                .addOnSuccessListener(
                    OnSuccessListener { taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                            val imageUrl = it.toString()
                            binding.profileProgressBar.visibility = View.GONE

                        }
                    }
                ).addOnFailureListener(
                    OnFailureListener { e ->
                        Log.d("profileImage", "${e.message}")
                        binding.profileProgressBar.visibility = View.GONE
                    }
                )



        }

    }

    override fun onDestroy() {
        super.onDestroy()
        profileBinding = null
    }

}