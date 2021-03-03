package com.solucionescreativasdemallorca.elitetop

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.solucionescreativasdemallorca.elitetop.base.BaseActivity
import com.solucionescreativasdemallorca.elitetop.login.LoginActivity

class SplashScreen : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // INITIALIZE FIREBASE IN ALL APP (VERY IMPORTANT)
        FirebaseApp.initializeApp(this)

        // Firebase Analytics
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val bundle = Bundle()
        bundle.putString("message", "Integración de Firebase completa")
        firebaseAnalytics.logEvent("InitScreen", bundle)

        // Firebase Authentication
        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

        // Autologin if firebase auth logged and not signout
        if (firebaseAuth.currentUser != null) {
            checkLoginAccountType(firebaseAuth)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                finish()
            }, 1000)
        }

    }
}