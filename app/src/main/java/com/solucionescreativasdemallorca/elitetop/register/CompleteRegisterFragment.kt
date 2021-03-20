package com.solucionescreativasdemallorca.elitetop.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.solucionescreativasdemallorca.elitetop.R
import com.solucionescreativasdemallorca.elitetop.base.BaseFragment
import java.util.*
import kotlin.collections.ArrayList


class CompleteRegisterFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_complete_register, container, false)

        val birthDateLayout: TextInputLayout =
            view.findViewById(R.id.completeregister_form_birthdate_material)
        val birth: TextInputEditText =
            view.findViewById(R.id.completeregister_form_birthdate_material_text)
        birthDateLayout.setOnClickListener { pickDate(view) }
        birth.setOnClickListener { pickDate(view) }

        // Spinner Genre
        val textViewGenre: AutoCompleteTextView =
            view.findViewById(R.id.completeregister_form_genre_material_dropdown)
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.genres_array,
                android.R.layout.simple_spinner_dropdown_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                textViewGenre.setAdapter(adapter)
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
            view.findViewById(R.id.completeregister_form_nationality_material_dropdown)
        val countryAdapter = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item, countries
            )
        }
        countryAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerNationality.setAdapter(countryAdapter)

        // On Clicks
        val btn: Button = view.findViewById(R.id.completeregister_form_btn)
        btn.setOnClickListener {
            goToLastStepRegister(view)
        }

        return view
    }

    private fun pickDate(view: View) {
        val materialDatePicker =
            MaterialDatePicker.Builder.datePicker().setTitleText("Select a date").build()
        materialDatePicker.addOnPositiveButtonClickListener {
            setDate(materialDatePicker.headerText.toString(), view)
        }
        parentFragmentManager.let { materialDatePicker.show(it, "MATERIAL_DATE_PICKER") }

    }

    private fun setDate(date: String, view: View) {
        val dateText: TextInputEditText =
            view.findViewById(R.id.completeregister_form_birthdate_material_text)
        dateText.setText(date)
    }

    private fun goToLastStepRegister(view: View) {
        val name: TextInputEditText =
            view.findViewById(R.id.completeregister_form_name_material_text)
        val surnames: TextInputEditText =
            view.findViewById(R.id.completeregister_form_surnames_material_text)
        val birth: TextInputEditText =
            view.findViewById(R.id.completeregister_form_birthdate_material_text)
        val textViewGenre: AutoCompleteTextView =
            view.findViewById(R.id.completeregister_form_genre_material_dropdown)
        val spinnerNationality: AutoCompleteTextView =
            view.findViewById(R.id.completeregister_form_nationality_material_dropdown)

        if (name.text.isNullOrBlank() || surnames.text.isNullOrBlank() || birth.text.isNullOrBlank() || textViewGenre.text
                .isNullOrBlank() || spinnerNationality.text.isNullOrBlank()
        ) {
            showMessage("¡No debe haber ningún campo vacío!")
        } else {
            (activity as RegisterActivity).user.name = name.text.toString()
            (activity as RegisterActivity).user.surnames = surnames.text.toString()
            (activity as RegisterActivity).user.birth = birth.text.toString()
            (activity as RegisterActivity).user.genre = textViewGenre.text.toString()
            (activity as RegisterActivity).user.nationality = spinnerNationality.text.toString()

            (activity as RegisterActivity).replaceFragment(
                R.id.register_fragment_container,
                AccountInfoRegisterFragment(),
                "AccountInfoRegisterFragment",
                arguments
            )
        }
    }
}