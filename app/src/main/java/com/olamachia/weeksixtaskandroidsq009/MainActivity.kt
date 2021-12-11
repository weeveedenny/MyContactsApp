package com.olamachia.weeksixtaskandroidsq009

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var mainButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainButton = findViewById(R.id.main_button)
        mainButton.setOnClickListener {
            val intent = Intent(  this, HomeActivity::class.java )
            startActivity(intent)

        }
    }
}