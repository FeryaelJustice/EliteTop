package com.solucionescreativasdemallorca.elitetop

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {

    private var check = 0

    private var accountTypeSelected: Boolean = false
    private var accountType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Hide top app bar (not android top bar)
        supportActionBar?.hide()

        // Spinner country Phone

        // Spinner account Type
        val textViewAccountType: AutoCompleteTextView =
            findViewById(R.id.register_form_accounttype_material_dropdown)
        ArrayAdapter.createFromResource(
            this,
            R.array.accountType_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            textViewAccountType.setAdapter(adapter)
        }
        textViewAccountType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (++check > 1) {
                    if (position != 0) {
                        val item = parent?.getItemAtPosition(position)
                        accountTypeSelected = true
                        accountType = item.toString()
                    } else {
                        accountTypeSelected = false
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                accountTypeSelected = false
            }
        }
    }

    fun showMessage(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun next(view: View) {
        val email: TextInputEditText = findViewById(R.id.register_form_email_material_text)
        val nickname: TextInputEditText = findViewById(R.id.register_form_nickname_material_text)
        val password: TextInputEditText = findViewById(R.id.register_form_password_material_text)
        val repeatPassword: TextInputEditText =
            findViewById(R.id.register_form_repeatpassword_material_text)
        val phone: TextInputEditText = findViewById(R.id.register_form_phone_material_text)

        if (email.text.isNullOrBlank() || nickname.text.isNullOrBlank() || password.text.isNullOrBlank() || repeatPassword.text.isNullOrBlank() || phone.text.isNullOrBlank() || !accountTypeSelected) {
            Toast.makeText(
                applicationContext,
                "¡No debe haber ningún campo vacío!",
                Toast.LENGTH_SHORT
            )?.show()
        } else {
            if (password.text?.toString() == repeatPassword.text?.toString()) {
                val bundle = Bundle()
                bundle.putString("email", email.text.toString())
                bundle.putString("nickname", nickname.text.toString())
                bundle.putString("password", password.text.toString())
                bundle.putString("phone", phone.text.toString())
                bundle.putString("accounttype", accountType)
                startActivity(
                    Intent(this, CompleteRegisterActivity::class.java).putExtras(bundle)
                )
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