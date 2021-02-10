package com.solucionescreativasdemallorca.elitetop

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Hide top app bar (not android top bar)
        supportActionBar?.hide()

        // Firebase
        initFirebase()
    }

    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser? = firebaseAuth.currentUser

    }

    fun initFirebase() {
        // Analytics
        firebaseAnalytics = Firebase.analytics
        val bundle = Bundle()
        bundle.putString("message", "Integración de Firebase completa")
        firebaseAnalytics.logEvent("InitScreen", bundle)

        // Authentication
        firebaseAuth = FirebaseAuth.getInstance()
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