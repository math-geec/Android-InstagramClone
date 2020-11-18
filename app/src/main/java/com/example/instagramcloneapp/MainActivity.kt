package com.example.instagramcloneapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.instagramcloneapp.Fragments.HomeFragment
import com.example.instagramcloneapp.Fragments.NotificationsFragment
import com.example.instagramcloneapp.Fragments.ProfileFragment
import com.example.instagramcloneapp.Fragments.SearchFragment

class MainActivity : AppCompatActivity() {

//    private lateinit var textView: TextView
    internal var selectedFragment: Fragment? = null

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
//                    textView.setText("Home")
                    selectedFragment = HomeFragment()
//                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_search -> {
//                    textView.setText("Search")
                    selectedFragment = SearchFragment()
//                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_add_post -> {
//                    textView.setText("Add Post")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_notifications -> {
//                    textView.setText("Notifications")
                    selectedFragment = NotificationsFragment()
//                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_profile -> {
//                    textView.setText("Profile")
                    selectedFragment = ProfileFragment()
//                    return@OnNavigationItemSelectedListener true
                }
            }

            if(selectedFragment != null){
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    selectedFragment!!
                ).commit()
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//        textView = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            HomeFragment()
        ).commit()

    }
}