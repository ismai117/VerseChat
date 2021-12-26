package com.im.versechat.ui.dashboard

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView

import com.google.firebase.auth.FirebaseAuth
import com.im.versechat.R
import com.im.versechat.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DashboardFragment
    @Inject
    constructor(
        private val fAuth: FirebaseAuth
    ): Fragment(), NavigationBarView.OnItemSelectedListener {

    private var dashboardBinding: FragmentDashboardBinding? = null
    private val binding get() = dashboardBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        dashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root

        val toolbar = binding.dashboardToolbar
        toolbar.setTitle("")

        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.messagesFragment,
            R.id.notificationFragment,
            R.id.profileFragment,
            R.id.settingsFragment
        ))

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.dashboard_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)


        binding.bottomNav.setOnItemSelectedListener(this)



        return view
    }

    override fun onStart() {
        super.onStart()

        val currentUser = fAuth.currentUser

        if (currentUser == null) {
            findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
        }

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.dashboard_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return item.onNavDestinationSelected(navController = navController)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.settings_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {


            R.id.logout -> {

                findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)

                return true
            }

            else ->{
                return super.onOptionsItemSelected(item)
            }

        }

    }



    override fun onDestroy() {
        super.onDestroy()
        dashboardBinding = null
    }

}