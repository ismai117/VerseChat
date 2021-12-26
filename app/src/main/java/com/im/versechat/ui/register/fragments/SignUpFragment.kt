package com.im.versechat.ui.register.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.im.versechat.R
import com.im.versechat.databinding.FragmentLoginBinding
import com.im.versechat.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.Text
import javax.inject.Inject


@AndroidEntryPoint
class SignUpFragment
@Inject
constructor(
    private val fAuth: FirebaseAuth,
    private val fStore: FirebaseFirestore
) : Fragment(){



    private var signupBinding: FragmentSignUpBinding? = null
    private val binding get() = signupBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        signupBinding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root





        binding.createBtn.setOnClickListener {


            val name = binding.signupNameInput.text.toString()
            val phone = binding.signupNumberInput.text.toString()
            val email = binding.signupEmailInput.text.toString()
            val password = binding.signupPasswordInput.text.toString()

            createUser(name, phone, email, password)

        }




        return view
    }

    private fun createUser(name: String, phone: String, email: String, password: String) {


        binding.SignUpProgressBar.isVisible = true

        when {


            TextUtils.isEmpty(name) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(email) && TextUtils.isEmpty(
                password) -> {
                binding.signupNameInput.error = "Don't leave field empty"
                binding.signupNumberInput.error = "Don't leave field empty"
                binding.signupEmailInput.error = "Don't leave field empty"
                binding.signupPasswordInput.error = "Don't leave field empty"
                binding.SignUpProgressBar.visibility = View.GONE
            }

            TextUtils.isEmpty(name) -> {
                binding.signupNameInput.error = "Don't leave field empty"
                binding.SignUpProgressBar.visibility = View.GONE
            }

            TextUtils.isEmpty(phone) -> {
                binding.signupNumberInput.error = "Don't leave field empty"
                binding.SignUpProgressBar.visibility = View.GONE
            }

            TextUtils.isEmpty(email) -> {
                binding.signupEmailInput.error = "Don't leave field empty"
                binding.SignUpProgressBar.visibility = View.GONE
            }

            TextUtils.isEmpty(password) -> {
                binding.signupPasswordInput.error = "Don't leave field empty"
                binding.SignUpProgressBar.visibility = View.GONE
            }

            else -> {


                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                    OnCompleteListener { task ->
                        if (task.isSuccessful) {

                            val userId = task.result?.user?.uid
                            Log.d("user_created", "success: userid = $userId")


                            val user = hashMapOf(
                                "name" to name,
                                "phone" to phone,
                                "email" to email,
                                "userId" to userId,
                                "image" to "default",
                                "status" to "default"
                            )

                            fStore.collection("users").document(userId.toString()).set(user)
                                .addOnSuccessListener(
                                    OnSuccessListener { documentSnapshot ->

                                        Log.d("user_created", "DocumentSnapshot: ${documentSnapshot}")
                                        findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)

                                        binding.SignUpProgressBar.visibility = View.GONE
                                    }
                                )
                                .addOnFailureListener(
                                    OnFailureListener { e ->
                                        Log.d("user_created", "${e.message}}")
                                        binding.SignUpProgressBar.visibility = View.GONE
                                    }
                                )

                        } else {
                            Log.d("user_created", "${task.exception?.message}")

                        }

                    }
                )

            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        signupBinding = null
    }

}