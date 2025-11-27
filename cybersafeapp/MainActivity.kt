package com.example.cybersafeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.cybersafeapp.ui.screens.CyberNewsFragment
import com.example.cybersafeapp.ui.screens.EmailLeakFragment
import com.example.cybersafeapp.ui.screens.GuideFragment
import com.example.cybersafeapp.ui.screens.IpCheckerFragment
import com.example.cybersafeapp.ui.screens.PasswordGeneratorFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_ip -> loadFragment(IpCheckerFragment())
                R.id.nav_news -> loadFragment(CyberNewsFragment())
                R.id.nav_guide -> loadFragment(GuideFragment())
                R.id.nav_email -> loadFragment(EmailLeakFragment())
                R.id.nav_password -> loadFragment(PasswordGeneratorFragment())
            }
            true
        }

        // domyślny ekran startowy
        bottomNav.selectedItemId = R.id.nav_ip
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
