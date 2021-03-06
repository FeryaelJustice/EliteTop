package com.solucionescreativasdemallorca.elitetop.main.athlete.screens.entrenamiento

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.solucionescreativasdemallorca.elitetop.R
import com.solucionescreativasdemallorca.elitetop.base.BaseFragment

class AthleteEntrenamientoFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(
            R.layout.fragment_athlete_entrenamiento,
            container, false
        )
        val toolbarImage: ImageView = requireActivity().findViewById(R.id.athlete_toolbarChat)
        toolbarImage.visibility = View.VISIBLE
        val toolbarProfile: ImageView = requireActivity().findViewById(R.id.athlete_toolbarProfile)
        toolbarProfile.visibility = View.GONE
        return rootView
    }

}