package com.example.notbored

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.notbored.databinding.ActivityBottomBinding
import com.example.notbored.ui.dashboard.RandomFragment
import com.example.notbored.ui.home.SelectorFragment

class BottomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val navView: BottomNavigationView = binding.navView

        val fragments = mapOf(
            R.id.navigation_selector to SelectorFragment(),
            R.id.navigation_random to RandomFragment()
        )

        //set initial fragment
        changeFragment(fragments[R.id.navigation_selector])
        navView.setOnNavigationItemSelectedListener {
            changeFragment(fragments[it.itemId])
            true
        }


    }

    private fun changeFragment(fragment: Fragment?) {
        requireNotNull(fragment){ "Fragment should not be null"}
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout,fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}