package com.solucionescreativasdemallorca.elitetop

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*
import kotlin.collections.ArrayList


class CompleteRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_register)

        // Hide top app bar (not android top bar)
        supportActionBar?.hide()

        val birthDateLayout: TextInputLayout =
            findViewById(R.id.completeregister_form_birthdate_material)
        val birth: TextInputEditText =
            findViewById(R.id.completeregister_form_birthdate_material_text)
        birthDateLayout.setOnClickListener { pickDate() }
        birth.setOnClickListener { pickDate() }

        // Spinner Genre
        val textViewGenre: AutoCompleteTextView =
            findViewById(R.id.completeregister_form_genre_material_dropdown)
        ArrayAdapter.createFromResource(
            this,
            R.array.genres_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            textViewGenre.setAdapter(adapter)
        }

        // Spinner Nationality
        val locales: Array<Locale> = Locale.getAvailableLocales()
        val countries = ArrayList<String>()
        for (locale in locales) {
            val country: String = locale.displayCountry
            if (country.trim { it <= ' ' }.isNotEmpty() && !countries.contains(country)) {
                countries.add(country)
            }
        }
        countries.sort()
        val spinnerNationality: AutoCompleteTextView =
            findViewById(R.id.completeregister_form_nationality_material_dropdown)
        val countryAdapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_item, countries
        )
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerNationality.setAdapter(countryAdapter)
    }

    fun pickDate() {
        var materialDatePicker =
            MaterialDatePicker.Builder.datePicker().setTitleText("Select a date").build()
        materialDatePicker.addOnPositiveButtonClickListener {
            setDate(materialDatePicker.headerText.toString())
        }
        materialDatePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")

    }

    fun setDate(date: String) {
        var dateText: TextInputEditText =
            findViewById(R.id.completeregister_form_birthdate_material_text)
        dateText.setText(date)
    }

    fun goToLastStepRegister(view: View?) {
        val name: TextInputEditText =
            findViewById(R.id.completeregister_form_name_material_text)
        val surnames: TextInputEditText =
            findViewById(R.id.completeregister_form_surnames_material_text)
        val birth: TextInputEditText =
            findViewById(R.id.completeregister_form_birthdate_material_text)
        val textViewGenre: AutoCompleteTextView =
            findViewById(R.id.completeregister_form_genre_material_dropdown)
        val spinnerNationality: AutoCompleteTextView =
            findViewById(R.id.completeregister_form_nationality_material_dropdown)

        if (name.text.isNullOrBlank() || surnames.text.isNullOrBlank() || birth.text.isNullOrBlank() || textViewGenre.text.toString()
                .isNullOrBlank() || spinnerNationality.text.toString().isNullOrBlank()
        ) {
            Toast.makeText(
                applicationContext,
                "¡No debe haber ningún campo vacío!",
                Toast.LENGTH_SHORT
            )?.show()
        } else {
            val bundle = Bundle()
            bundle.putString("name", name.text.toString())
            bundle.putString("surnames", surnames.text.toString())
            bundle.putString("birth", birth.text.toString())
            bundle.putString("textViewGenre", textViewGenre.text.toString())
            bundle.putString("spinnerNationality", spinnerNationality.text.toString())
            startActivity(
                intent.extras?.let {
                    Intent(this, AdditionalInfoRegisterActivity::class.java).putExtras(bundle)
                        .putExtras(
                            it
                        )
                }
            )
            finish()
        }
    }
}