package com.solucionescreativasdemallorca.elitetop

import android.os.Bundle

class RegisterActivity : BaseActivity() {

    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Hide top app bar (not android top bar)
        supportActionBar?.hide()

        user = User("", "", "", "", "", "", "", "", "", "", "")

        addFragment(R.id.register_fragment_container, RegisterFragment(), "RegisterFragment")
    }

}