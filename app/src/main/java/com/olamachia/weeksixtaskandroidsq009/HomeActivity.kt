package com.olamachia.weeksixtaskandroidsq009

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {
    lateinit private var nav_button : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_activity)

        anotherFragment(AddContactFragment())
        nav_button  = findViewById(R.id.menu_bottomnav)

        nav_button.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.menu_addcontact -> anotherFragment(AddContactFragment())
                R.id.menu_database -> anotherFragment(ReadContactFragment())
                R.id.menu_profile -> anotherFragment(Profilefragment())
            }
            true
        }

    }

    private fun anotherFragment(fragment : Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}