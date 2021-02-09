package com.solucionescreativasdemallorca.elitetop

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Hide top app bar (not android top bar)
        supportActionBar?.hide()
    }

    fun register(view: View) {
        val email: TextInputEditText = findViewById(R.id.register_form_email_material_text)
        val password: TextInputEditText = findViewById(R.id.register_form_password_material_text)

        if (email.text.isNullOrBlank() || password.text.isNullOrBlank()) {
            Toast.makeText(
                applicationContext,
                "¡Debes no introducir un email o contraseña vacíos!",
                Toast.LENGTH_SHORT
            )?.show()
        } else {
            Toast.makeText(
                applicationContext,
                "${email.text} ${password.text}",
                Toast.LENGTH_SHORT
            )?.show()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}