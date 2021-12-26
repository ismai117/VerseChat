package com.im.versechat.ui.login

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
import com.google.firebase.auth.FirebaseAuth
import com.im.versechat.R
import com.im.versechat.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment
@Inject
constructor(
    private val fAuth: FirebaseAuth,
) : Fragment() {

    private var loginBinding: FragmentLoginBinding? = null
    private val binding get() = loginBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        loginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root





        binding.loginBtn.setOnClickListener {


            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            login(email, password)


        }

        binding.gotoForgetpassword.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_forgetPasswordFragment) }

        binding.gotosignup.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_signUpFragment) }



        return view
    }

    private fun login(email: String, password: String) {


        binding.LoginProgressBar.isVisible = true

        when {


            TextUtils.isEmpty(email) && TextUtils.isEmpty(
                password) -> {
                binding.emailInput.error = "Don't leave field empty"
                binding.LoginProgressBar.visibility = View.GONE

            }

            TextUtils.isEmpty(email) -> {
                binding.emailInput.error = "Don't leave field empty"
                binding.LoginProgressBar.visibility = View.GONE
            }

            TextUtils.isEmpty(password) -> {
                binding.passwordInput.error = "Don't leave field empty"
                binding.LoginProgressBar.visibility = View.GONE
            }

            else -> {


                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                    OnCompleteListener { task ->
                        if (task.isSuccessful) {

                            Log.d("login", "login successful")

                            binding.LoginProgressBar.visibility = View.GONE
                            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)

                        } else {
                            Log.d("login", "${task.exception?.message}")
                            binding.LoginProgressBar.visibility = View.GONE
                        }
                    }
                )

            }


        }

    }

    override fun onDestroy() {
        super.onDestroy()
        loginBinding = null
    }

}