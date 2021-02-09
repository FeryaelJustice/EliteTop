package com.solucionescreativasdemallorca.elitetop

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CompleteRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_register)

        Toast.makeText(
            this,
            "${intent.getStringExtra("email").toString()} ${
                intent.getStringExtra("nickname").toString()
            } ${intent.getStringExtra("password").toString()} ${
                intent.getStringExtra("phone").toString()
            } ${intent.getStringExtra("accounttype").toString()} ",
            Toast.LENGTH_SHORT
        )
    }
}