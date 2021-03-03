package com.solucionescreativasdemallorca.elitetop.main.athlete

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.solucionescreativasdemallorca.elitetop.R
import com.solucionescreativasdemallorca.elitetop.base.BaseActivity
import com.solucionescreativasdemallorca.elitetop.login.LoginActivity

class AthleteActivity : BaseActivity() {

    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_athlete)

        firebaseUser = FirebaseAuth.getInstance().currentUser

        val signOutButton: ImageView = findViewById(R.id.activity_athlete_toolbarRightButton)
        signOutButton.setOnClickListener {
            if (signOut()) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        signOut()
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