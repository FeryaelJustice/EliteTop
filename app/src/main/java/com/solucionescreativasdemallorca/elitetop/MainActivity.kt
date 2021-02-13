package com.solucionescreativasdemallorca.elitetop

import android.os.Bundle
import com.solucionescreativasdemallorca.elitetop.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide top app bar (not android top bar)
        supportActionBar?.hide()
    }
}