package com.solucionescreativasdemallorca.elitetop.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.solucionescreativasdemallorca.elitetop.R
import com.solucionescreativasdemallorca.elitetop.base.BaseFragment
import com.solucionescreativasdemallorca.elitetop.main.athlete.AthleteActivity


class AccountInfoRegisterFragment : BaseFragment() {

    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_account_info_register, container, false)

        // Get Auth
        auth = FirebaseAuth.getInstance()

        // On Clicks
        val btn: Button = view.findViewById(R.id.additionalinforegister_form_btn)
        btn.setOnClickListener {
            register(view)
        }

        return view
    }

    private fun register(view: View) {
        val sports: TextInputEditText =
            view.findViewById(R.id.additionalinforegister_form_sports_material_text)
        (activity as RegisterActivity).user.sports = sports.text.toString()

        auth.createUserWithEmailAndPassword(
            (activity as RegisterActivity).user.email,
            (activity as RegisterActivity).user.password
        )
            .addOnCompleteListener { it ->
                if (it.isSuccessful) {
                    // Add a new document with a new generated User
                    val currentFirebaseUser: FirebaseUser? =
                        auth.currentUser
                    val db = FirebaseFirestore.getInstance()
                    val document: DocumentReference =
                        db.collection("users").document(currentFirebaseUser?.uid.toString())
                    document.set((activity as RegisterActivity).user).addOnCompleteListener {
                        val accountType: String = (activity as RegisterActivity).user.accountType
                        accountType.let {
                            if (accountType == "Atleta") {
                                (activity as RegisterActivity).startActivity(
                                    Intent(
                                        activity,
                                        AthleteActivity::class.java
                                    )
                                )
                            } else {
                                showMessage("Cuenta de entrenador")
                            }
                        }
                    }
                } else {
                    showMessage("Error al registrarse")
                }
            }
    }
}