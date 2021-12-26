package com.im.versechat.ui.forgetpassword

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.im.versechat.R
import com.im.versechat.databinding.FragmentForgetPasswordBinding
import com.im.versechat.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForgetPasswordFragment
@Inject
constructor(
    private val fAuth: FirebaseAuth,
) : Fragment(){


    private var forgetpasswordBinding: FragmentForgetPasswordBinding? = null
    private val binding get() = forgetpasswordBinding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        forgetpasswordBinding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        val view = binding.root





        binding.submitFpBtn.setOnClickListener {

            val email = binding.fpEmailInput.text.toString()

            resetPassword(email)
        }



        binding.fpGotoLogIn.setOnClickListener { findNavController().navigate(R.id.action_forgetPasswordFragment_to_loginFragment) }




        return view
    }

    private fun resetPassword(email: String) {


        binding.fpProgressBar.isVisible = true

        when {


            TextUtils.isEmpty(email) -> {

                binding.fpEmailInput.error = "Don't leave field empty"

                binding.fpProgressBar.visibility = View.VISIBLE

            }

            else -> {


                fAuth.sendPasswordResetEmail(email).addOnCompleteListener(
                    OnCompleteListener { task ->

                        if (task.isSuccessful) {


                            Log.d("resetPassword", "reset password link sent to inbox")

                            Toast.makeText(requireContext(),
                                "Reset Password link sent to inbox",
                                Toast.LENGTH_LONG).show()

                            binding.fpGotoLogIn.setOnClickListener { findNavController().navigate(R.id.action_forgetPasswordFragment_to_loginFragment) }

                            binding.fpProgressBar.visibility = View.VISIBLE

                        } else {

                            Log.d("resetPassword", "${task.exception?.message}")

                            binding.fpProgressBar.visibility = View.VISIBLE
                        }

                    }
                )


            }

        }

    }


    override fun onDestroy() {
        super.onDestroy()
        forgetpasswordBinding = null
    }


}