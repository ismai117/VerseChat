package com.im.versechat.ui.dashboard

import android.content.Context
import androidx.navigation.fragment.NavHostFragment
import com.im.versechat.DefaultFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardNavHostFragment : NavHostFragment(){
    
    @Inject
    lateinit var defaultFragmentFactory: DefaultFragmentFactory


    override fun onAttach(context: Context) {
        super.onAttach(context)

        childFragmentManager.fragmentFactory = defaultFragmentFactory

    }



}