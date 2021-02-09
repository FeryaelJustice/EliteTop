package com.solucionescreativasdemallorca.elitetop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide top app bar (not android top bar)
        supportActionBar?.hide()
    }
}