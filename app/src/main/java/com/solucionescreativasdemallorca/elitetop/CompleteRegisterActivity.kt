package com.solucionescreativasdemallorca.elitetop

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*
import kotlin.collections.ArrayList


class CompleteRegisterActivity : AppCompatActivity() {

    private var check = 0

    private var genreSelected: Boolean = false
    private var genre: String = ""
    private var nationalitySelected: Boolean = false
    private var nationality: String = ""

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
        textViewGenre.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (++check > 1) {
                    if (position != 0) {
                        val item = parent?.getItemAtPosition(position)
                        genreSelected = true
                        genre = item.toString()
                    } else {
                        genreSelected = false
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                genreSelected = false
            }
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
        spinnerNationality.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (++check > 1) {
                    if (position != 0) {
                        val item = parent?.getItemAtPosition(position)?.toString()
                        nationalitySelected = true
                    } else {
                        nationalitySelected = false
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                nationalitySelected = false
            }
        }
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

    fun register(view: View?) {

    }
}