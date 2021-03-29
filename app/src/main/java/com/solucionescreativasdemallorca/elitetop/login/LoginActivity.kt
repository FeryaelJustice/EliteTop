package com.solucionescreativasdemallorca.elitetop.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.solucionescreativasdemallorca.elitetop.R
import com.solucionescreativasdemallorca.elitetop.base.BaseActivity
import com.solucionescreativasdemallorca.elitetop.base.LoginType
import com.solucionescreativasdemallorca.elitetop.recoverpassword.RecoverPasswordActivity
import com.solucionescreativasdemallorca.elitetop.register.RegisterActivity


class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // On Clicks
        val btn: Button = findViewById(R.id.login_form_btn)
        btn.setOnClickListener {
            loginCredentials()
        }
        val recoverAccount: TextView = findViewById(R.id.login_form_recoveraccount)
        recoverAccount.setOnClickListener {
            recoverAccount()
        }
        val register: TextView = findViewById(R.id.login_form_register)
        register.setOnClickListener {
            navigateRegister()
        }
    }

    private fun loginCredentials() {
        val email: TextInputEditText = findViewById(R.id.login_form_email_material_text)
        val password: TextInputEditText = findViewById(R.id.login_form_password_material_text)
        if (email.text.isNullOrBlank() || password.text.isNullOrBlank()) {
            showMessage("¡No debe haber ningún campo vacío!")
        } else {
            login(email.text.toString(), password.text.toString(), LoginType.DEFAULT)
        }
    }

    private fun recoverAccount() {
        startActivity(Intent(this, RecoverPasswordActivity::class.java))
    }

    private fun navigateRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}