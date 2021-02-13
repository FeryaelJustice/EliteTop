package com.solucionescreativasdemallorca.elitetop.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.solucionescreativasdemallorca.elitetop.MainActivity
import com.solucionescreativasdemallorca.elitetop.R
import com.solucionescreativasdemallorca.elitetop.base.BaseFragment


class AccountInfoRegisterFragment : BaseFragment() {

    lateinit var storage: FirebaseStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_account_info_register, container, false)

        storage = Firebase.storage
        var storageRef = storage.reference

        // On Clicks
        val btn: Button = view.findViewById(R.id.additionalinforegister_form_btn)
        btn.setOnClickListener {
            register(view)
        }

        return view
    }

    override fun onDestroyView() {
        val childFragment: Fragment? =
            parentFragmentManager.findFragmentByTag("AccountInfoRegisterFragment")
        childFragment?.let {
            parentFragmentManager.beginTransaction().remove(it).commit()
        }
        super.onDestroyView()
    }

    private fun register(view: View) {
        val sports: TextInputEditText =
            view.findViewById(R.id.additionalinforegister_form_sports_material_text)
        (activity as RegisterActivity).user.sports = sports.text.toString()

        Log.d("usuario", (activity as RegisterActivity).user.toString())

        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(
                (activity as RegisterActivity).user.email,
                (activity as RegisterActivity).user.password
            )
            .addOnCompleteListener { it ->
                if (it.isSuccessful) {
                    // Add a new document with a new generated User
                    val currentFirebaseUser: FirebaseUser? =
                        FirebaseAuth.getInstance().currentUser
                    val db = FirebaseFirestore.getInstance()
                    val document: DocumentReference =
                        db.collection("users").document(currentFirebaseUser?.uid.toString())
                    document.set((activity as RegisterActivity).user).addOnCompleteListener {
                        (activity as RegisterActivity).startActivity(
                            Intent(
                                activity,
                                MainActivity::class.java
                            )
                        )
                        (activity as RegisterActivity).finish()

                    }
                } else {
                    showMessage("Error al registrarse")
                }
            }
    }
}