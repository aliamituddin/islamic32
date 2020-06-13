package com.route.islami.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.route.islami.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener {
            val id = it.itemId;
            var fragment: Fragment =
                when (id) {
                    R.id.navigation_quran -> {
                        QuranFragment()
                    }
                    R.id.navigation_radio -> {
                        RadioFragment()
                    }
                    else -> QuranFragment()
                }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()

            return@setOnNavigationItemSelectedListener true;

        }
    }
}