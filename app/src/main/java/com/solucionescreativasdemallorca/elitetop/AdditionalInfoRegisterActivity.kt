package com.solucionescreativasdemallorca.elitetop

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class AdditionalInfoRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additional_info_register)

        // On Clicks
        val btn: Button = findViewById(R.id.additionalinforegister_form_btn)
        btn.setOnClickListener {
            register()
        }
    }

    private fun register() {
        intent?.let { intent ->
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(
                    intent.getStringExtra("email").toString(),
                    intent.getStringExtra("password").toString()
                )
                .addOnCompleteListener { it ->
                    if (it.isSuccessful) {
                        val db = FirebaseFirestore.getInstance()

                        // Create a new user with a first and last name
                        val user: MutableMap<String, Any> = HashMap()
                        user["email"] = intent.getStringExtra("email").toString()
                        user["nickname"] = intent.getStringExtra("nickname").toString()
                        user["password"] = intent.getStringExtra("password").toString()
                        user["phone"] = intent.getStringExtra("phone").toString()
                        user["accountType"] = intent.getStringExtra("accountType").toString()
                        user["name"] = intent.getStringExtra("name").toString()
                        user["surnames"] = intent.getStringExtra("surnames").toString()
                        user["birth"] = intent.getStringExtra("birth").toString()
                        user["genre"] = intent.getStringExtra("genre").toString()
                        user["nationality"] = intent.getStringExtra("nationality").toString()

                        // Add a new document with a new generated User
                        val currentFirebaseUser: FirebaseUser? =
                            FirebaseAuth.getInstance().currentUser
                        currentFirebaseUser?.let {
                            val document: DocumentReference =
                                db.collection("users").document(it.uid)
                            document.set(user).addOnCompleteListener {
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                        }
                    } else {
                        showMessage("Error al registrarse")
                    }
                }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            applicationContext,
            message,
            Toast.LENGTH_SHORT
        )?.show()
    }
}