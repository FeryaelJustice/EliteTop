package com.solucionescreativasdemallorca.elitetop

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Hide top app bar (not android top bar)
        supportActionBar?.hide()
    }

    fun login(view: View) {
        val email: TextInputEditText = findViewById(R.id.login_form_email_material_text)
        val password: TextInputEditText = findViewById(R.id.login_form_password_material_text)

        if (email.text.isNullOrBlank() || password.text.isNullOrBlank()) {
            Toast.makeText(
                applicationContext,
                "¡No debe haber ningún campo vacío!",
                Toast.LENGTH_SHORT
            )?.show()
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    fun recoverAccount(view: View) {
        Toast.makeText(
            applicationContext,
            "¡Recuperar contraseña!",
            Toast.LENGTH_SHORT
        )?.show()
    }

    fun navigateRegister(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}