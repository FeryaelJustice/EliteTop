package com.solucionescreativasdemallorca.elitetop.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.ktx.Firebase
import com.solucionescreativasdemallorca.elitetop.R
import com.solucionescreativasdemallorca.elitetop.base.BaseActivity
import com.solucionescreativasdemallorca.elitetop.main.athlete.AthleteActivity
import com.solucionescreativasdemallorca.elitetop.recoverpassword.RecoverPasswordActivity
import com.solucionescreativasdemallorca.elitetop.register.RegisterActivity


class LoginActivity : BaseActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // INITIALIZE FIREBASE IN ALL APP (VERY IMPORTANT)
        FirebaseApp.initializeApp(this)

        // Firebase Analytics
        firebaseAnalytics = Firebase.analytics
        val bundle = Bundle()
        bundle.putString("message", "Integración de Firebase completa")
        firebaseAnalytics.logEvent("InitScreen", bundle)

        // Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance()

        // On Clicks
        val btn: Button = findViewById(R.id.login_form_btn)
        btn.setOnClickListener {
            login()
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

    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        firebaseAuth.currentUser.let {
            //val currentUser: FirebaseUser? = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        FirebaseAuth.getInstance().signOut()
    }

    private fun login() {
        val email: TextInputEditText = findViewById(R.id.login_form_email_material_text)
        val password: TextInputEditText = findViewById(R.id.login_form_password_material_text)
        if (email.text.isNullOrBlank() || password.text.isNullOrBlank()) {
            showMessage("¡No debe haber ningún campo vacío!")
        } else {
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener { it ->
                    if (it.isSuccessful) {
                        val db = FirebaseFirestore.getInstance()
                        val document: DocumentReference =
                            db.collection("users")
                                .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                        val source = Source.DEFAULT
                        document.get(source).addOnSuccessListener { doc ->
                            if (doc != null) {
                                val accountType: String =
                                    doc.data?.get("accountType").toString()
                                accountType.let { type ->
                                    if (type == "Atleta") {
                                        startActivity(Intent(this, AthleteActivity::class.java))
                                        finish()
                                    } else {
                                        showMessage("Cuenta de entrenador")
                                    }

                                }
                            } else {
                                Log.d("TAG", "No such document")
                            }
                        }.addOnFailureListener { exception ->
                            Log.d("TAG", "get failed with ", exception)
                        }
                    } else {
                        showMessage("Error al iniciar sesión")
                    }
                }
        }
    }

    private fun recoverAccount() {
        startActivity(Intent(this, RecoverPasswordActivity::class.java))
    }

    private fun navigateRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}