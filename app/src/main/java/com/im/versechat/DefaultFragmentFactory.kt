package com.im.versechat

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.im.versechat.ui.dashboard.DashboardFragment
import com.im.versechat.ui.dashboard.fragments.messages.fragments.chat.fragments.ChatFragment
import com.im.versechat.ui.dashboard.fragments.messages.fragments.messages.fragments.MessagesFragment
import com.im.versechat.ui.dashboard.fragments.notification.fragments.NotificationFragment
import com.im.versechat.ui.dashboard.fragments.profile.fragments.ProfileFragment
import com.im.versechat.ui.dashboard.fragments.settings.fragments.SettingsFragment
import com.im.versechat.ui.dashboard.fragments.users.fragments.UsersFragment
import com.im.versechat.ui.forgetpassword.ForgetPasswordFragment
import com.im.versechat.ui.login.LoginFragment
import com.im.versechat.ui.register.fragments.SignUpFragment
import javax.inject.Inject


class DefaultFragmentFactory
@Inject
constructor(
    private val fAuth: FirebaseAuth,
    private val fStore: FirebaseFirestore,
    private val fStorage: FirebaseStorage,
) : FragmentFactory() {


    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {


        return when (className) {


            LoginFragment::class.java.name -> {
                return LoginFragment(fAuth)
            }

            SignUpFragment::class.java.name -> {
                return SignUpFragment(fAuth, fStore)
            }

            ForgetPasswordFragment::class.java.name -> {
                return ForgetPasswordFragment(fAuth)
            }

            DashboardFragment::class.java.name -> {
                return DashboardFragment(fAuth)
            }

            MessagesFragment::class.java.name -> {
                return MessagesFragment(fAuth, fStore)
            }

            NotificationFragment::class.java.name -> {
                return NotificationFragment(fAuth)
            }

            ProfileFragment::class.java.name -> {
                return ProfileFragment(fAuth, fStore, fStorage)
            }

            SettingsFragment::class.java.name -> {
                return SettingsFragment(fAuth)
            }

            UsersFragment::class.java.name -> {
                return UsersFragment(fAuth, fStore)
            }

            ChatFragment::class.java.name -> {
                return ChatFragment(fAuth, fStore)
            }

            else -> {
                super.instantiate(classLoader, className)
            }

        }


    }


}