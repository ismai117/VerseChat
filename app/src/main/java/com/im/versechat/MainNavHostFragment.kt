package com.im.versechat

import android.content.Context
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainNavHostFragment: NavHostFragment(){


    @Inject
    lateinit var defaultFragmentFactory: DefaultFragmentFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)

        childFragmentManager.fragmentFactory = defaultFragmentFactory
    }

}