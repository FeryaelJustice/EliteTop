package com.solucionescreativasdemallorca.elitetop.main.athlete.screens.resultados

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.solucionescreativasdemallorca.elitetop.R
import com.solucionescreativasdemallorca.elitetop.base.BaseFragment

class AthleteResultadosFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(
            R.layout.fragment_athlete_resultados,
            container, false
        )
        val toolbarImage: ImageView = requireActivity().findViewById(R.id.athlete_toolbarChat)
        toolbarImage.visibility = View.VISIBLE
        val toolbarProfile: ImageView = requireActivity().findViewById(R.id.athlete_toolbarProfile)
        toolbarProfile.visibility = View.GONE
        return rootView
    }

}