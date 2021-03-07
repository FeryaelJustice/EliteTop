package com.solucionescreativasdemallorca.elitetop.base

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.solucionescreativasdemallorca.elitetop.login.LoginActivity
import com.solucionescreativasdemallorca.elitetop.main.athlete.AthleteActivity

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var firebaseAnalytics: FirebaseAnalytics

    protected fun showMessage(message: String) {
        Toast.makeText(
            applicationContext,
            message,
            Toast.LENGTH_SHORT
        )?.show()
    }

    // FRAGMENTS

    fun addFragment(containerIdLayout: Int, fragmentClass: BaseFragment, tag: String) {
        fragmentClass.arguments = intent.extras

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(containerIdLayout, fragmentClass, tag)
        }

    }

    fun replaceFragment(containerIdLayout: Int, fragmentClass: BaseFragment, tag: String) {
        fragmentClass.arguments = intent.extras

        /*val displayedFragment = supportFragmentManager.findFragmentByTag(tag)
        displayedFragment?.let {
            val sameClass: Boolean = displayedFragment::class == fragmentClass::class
            if (!sameClass) {
                supportFragmentManager.beginTransaction()
                    .replace(containerIdLayout, fragmentClass, tag).addToBackStack(javaClass.name)
                    .setReorderingAllowed(true)
                    .commit()
            }
        }*/
        supportFragmentManager.beginTransaction()
            .replace(containerIdLayout, fragmentClass, tag).addToBackStack(javaClass.name)
            .setReorderingAllowed(true)
            .commit()

    }

    // FIREBASE METHODS

    protected open fun login(email: String, password: String, type: LoginType) {
        // Get Auth
        val auth = FirebaseAuth.getInstance()
        when (type) {
            LoginType.DEFAULT -> auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { it ->
                    if (it.isSuccessful) {
                        checkLoginAccountType(auth)
                    } else {
                        showMessage("Error al iniciar sesión")
                    }
                }
        }
    }

    // Login checking account type. Requires: a FirebaseAuth object instantiated
    protected open fun checkLoginAccountType(firebaseAuth: FirebaseAuth) {
        val db = FirebaseFirestore.getInstance()
        val document: DocumentReference =
            db.collection("users")
                .document(firebaseAuth.currentUser?.uid.toString())
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
    }

    protected open fun logOut() {
        if (signOut()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun signOut(): Boolean {
        return try {
            FirebaseAuth.getInstance().signOut()
            true
        } catch (e: Exception) {
            false
        }
    }

}