package com.solucionescreativasdemallorca.elitetop.recoverpassword

import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.solucionescreativasdemallorca.elitetop.R
import com.solucionescreativasdemallorca.elitetop.base.BaseActivity
import java.util.*


class RecoverPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password)

        // Hide top app bar (not android top bar)
        supportActionBar?.hide()

        // On Clicks
        val btn: Button = findViewById(R.id.recoverPassword_form_btn)
        btn.setOnClickListener {
            sendRecoverMail()
        }
    }

    fun sendRecoverMail() {
        val email: TextInputEditText = findViewById(R.id.recoverPassword_form_email_material_text)
        email.let {
            val firebaseAuth: FirebaseAuth = Firebase.auth
            if (Locale.getDefault().displayLanguage.equals("anglais")) {
                firebaseAuth.setLanguageCode("en")
            } else {
                firebaseAuth.setLanguageCode("es")
            }
            firebaseAuth.sendPasswordResetEmail(it.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showMessage("Email enviado a tu correo")
                        finish()
                    } else {
                        showMessage("Error al enviar el correo, no existe esta cuenta.")
                    }
                }
        }

    }
}