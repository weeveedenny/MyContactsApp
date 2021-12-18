package com.olamachia.weeksixtaskandroidsq009

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_activity)
        addFragmentToActivity(AddContactFragment())
        bottomNavigationView  = findViewById(R.id.menu_bottomnav)

        bottomNavigationView.setOnItemSelectedListener{
            when (it.itemId) {
                R.id.menu_addcontact -> addFragmentToActivity(AddContactFragment())
                R.id.menu_database -> addFragmentToActivity(ReadContactFragment())
                R.id.menu_profile -> addFragmentToActivity(Profilefragment())
            }
            true
        }

    }

    private fun addFragmentToActivity(fragment : Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}