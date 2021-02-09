package com.solucionescreativasdemallorca.elitetop

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    var email: TextInputEditText = TextInputEditText(applicationContext)
    var password: TextInputEditText = TextInputEditText(applicationContext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /*
        val textInputLayout = TextInputLayout(this)
        val editText = TextInputEditText(textInputLayout.context)
        */

        email = findViewById(R.id.login_form_email_material_text)
        password = findViewById(R.id.login_form_password_material_text)
    }

    fun login() {
        var emailValue = email.text
        var passwordValue = password.text

        Toast.makeText(applicationContext, "${emailValue} ${passwordValue}", Toast.LENGTH_LONG)
            ?.show()
    }
}