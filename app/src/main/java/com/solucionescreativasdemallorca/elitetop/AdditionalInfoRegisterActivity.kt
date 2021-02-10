package com.solucionescreativasdemallorca.elitetop

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class AdditionalInfoRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additional_info_register)
    }

    fun register(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}