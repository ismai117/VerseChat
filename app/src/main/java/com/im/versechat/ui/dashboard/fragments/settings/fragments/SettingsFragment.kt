package com.im.versechat.ui.dashboard.fragments.settings.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.im.versechat.R
import com.im.versechat.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment
@Inject
constructor(
    private val fAuth: FirebaseAuth,
) : Fragment(){


    private var settingsBinding: FragmentSettingsBinding? = null
    private val binding get() = settingsBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        settingsBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root





        return view
    }

}