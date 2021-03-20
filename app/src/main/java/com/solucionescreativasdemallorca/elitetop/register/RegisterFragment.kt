package com.solucionescreativasdemallorca.elitetop.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.solucionescreativasdemallorca.elitetop.R
import com.solucionescreativasdemallorca.elitetop.base.BaseFragment

class RegisterFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_register, container, false)

        // Spinner account Type
        val textViewAccountType: AutoCompleteTextView =
            view.findViewById(R.id.register_form_accounttype_material_dropdown)
        ArrayAdapter.createFromResource(
            view.context,
            R.array.accountType_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            textViewAccountType.setAdapter(adapter)
        }

        // On Clicks
        val btn: Button = view.findViewById(R.id.register_form_btn)
        btn.setOnClickListener {
            next(view)
        }

        return view
    }

    /*
    override fun onDestroyView() {
        val childFragment: Fragment? = parentFragmentManager.findFragmentByTag("RegisterFragment")
        childFragment?.let {
            parentFragmentManager.beginTransaction().remove(it).commit()
        }
        super.onDestroyView()
    }*/

    private fun next(view: View) {
        val email: TextInputEditText = view.findViewById(R.id.register_form_email_material_text)
        val nickname: TextInputEditText =
            view.findViewById(R.id.register_form_nickname_material_text)
        val password: TextInputEditText =
            view.findViewById(R.id.register_form_password_material_text)
        val repeatPassword: TextInputEditText =
            view.findViewById(R.id.register_form_repeatpassword_material_text)
        val phone: TextInputEditText = view.findViewById(R.id.register_form_phone_material_text)
        val textViewAccountType: AutoCompleteTextView =
            view.findViewById(R.id.register_form_accounttype_material_dropdown)

        if (email.text.isNullOrBlank() || nickname.text.isNullOrBlank() || password.text.isNullOrBlank() || repeatPassword.text.isNullOrBlank() || phone.text.isNullOrBlank() || textViewAccountType.text
                .isNullOrBlank()
        ) {
            showMessage("¡No debe haber ningún campo vacío!")
        } else {
            if (password.text?.toString() == repeatPassword.text?.toString()) {
                (activity as RegisterActivity).user.email = email.text.toString()
                (activity as RegisterActivity).user.nickname = nickname.text.toString()
                (activity as RegisterActivity).user.password = password.text.toString()
                (activity as RegisterActivity).user.phone = phone.text.toString()
                (activity as RegisterActivity).user.accountType =
                    textViewAccountType.text.toString()

                (activity as RegisterActivity).replaceFragment(
                    R.id.register_fragment_container,
                    CompleteRegisterFragment(),
                    "CompleteRegisterFragment",
                    arguments
                )
            } else {
                showMessage("¡Las contraseñas deben coincidir!")
            }
        }
    }
}