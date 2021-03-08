package com.solucionescreativasdemallorca.elitetop.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

abstract class BaseFragment : Fragment() {

    protected fun showMessage(message: String) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        )?.show()
    }

    protected fun signOut(): Boolean {
        return try {
            FirebaseAuth.getInstance().signOut()
            true
        } catch (e: Exception) {
            false
        }
    }


}