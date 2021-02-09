package com.solucionescreativasdemallorca.elitetop

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val spinner: Spinner = findViewById(R.id.register_form_accounttype)
        spinner.adapter = ArrayAdapter<AccountType>(
            this,
            android.R.layout.simple_spinner_item,
            AccountType.values()
        )
        spinner.setSelection(0)

        // Hide top app bar (not android top bar)
        supportActionBar?.hide()
    }

    fun register(view: View) {
        val email: TextInputEditText = findViewById(R.id.register_form_email_material_text)
        val nickname: TextInputEditText = findViewById(R.id.register_form_nickname_material_text)
        val password: TextInputEditText = findViewById(R.id.register_form_password_material_text)
        val repeatPassword: TextInputEditText =
            findViewById(R.id.register_form_repeatpassword_material_text)
        val phone: TextInputEditText = findViewById(R.id.register_form_phone_material_text)
        val accountType: Spinner = findViewById(R.id.register_form_accounttype)

        if (email.text.isNullOrBlank() || nickname.text.isNullOrBlank() || password.text.isNullOrBlank() || repeatPassword.text.isNullOrBlank() || phone.text.isNullOrBlank()) {
            Toast.makeText(
                applicationContext,
                "¡No debe haber ningún campo vacío!",
                Toast.LENGTH_SHORT
            )?.show()
        } else {
            if (password.text?.toString() == repeatPassword.text?.toString()) {
                startActivity(Intent(this, CompleteRegisterActivity::class.java).apply {
                    putExtra("email", email.text.toString())
                    putExtra("nickname", nickname.text.toString())
                    putExtra("password", password.text.toString())
                    putExtra("phone", phone.text.toString())
                    putExtra("accounttype", accountType.selectedItem?.toString().toString())
                })
                finish()
            } else {
                Toast.makeText(
                    applicationContext,
                    "¡Las contraseñas deben coincidir!",
                    Toast.LENGTH_SHORT
                )?.show()
            }
        }
    }
}