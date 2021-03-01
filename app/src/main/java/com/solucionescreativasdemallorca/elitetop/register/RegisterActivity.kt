package com.solucionescreativasdemallorca.elitetop.register

import android.os.Bundle
import com.solucionescreativasdemallorca.elitetop.R
import com.solucionescreativasdemallorca.elitetop.base.BaseActivity
import com.solucionescreativasdemallorca.elitetop.dataclass.User

class RegisterActivity : BaseActivity() {

    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        user = User("", "", "", "", "", "", "", "", "", "", "")

        addFragment(R.id.register_fragment_container, RegisterFragment(), "RegisterFragment")
    }

}