package com.solucionescreativasdemallorca.elitetop.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

abstract class BaseActivity : AppCompatActivity() {

    protected fun showMessage(message: String) {
        Toast.makeText(
            applicationContext,
            message,
            Toast.LENGTH_SHORT
        )?.show()
    }

    // FRAGMENTS

    fun addFragment(containerIdLayout: Int, fragmentClass: BaseFragment, tag: String) {
        fragmentClass.arguments = intent.extras

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(containerIdLayout, fragmentClass, tag)
        }

    }

    fun replaceFragment(containerIdLayout: Int, fragmentClass: BaseFragment, tag: String) {
        fragmentClass.arguments = intent.extras

        supportFragmentManager.beginTransaction()
            .replace(containerIdLayout, fragmentClass, tag).addToBackStack(javaClass.name)
            .setReorderingAllowed(true)
            .commit()
    }


}