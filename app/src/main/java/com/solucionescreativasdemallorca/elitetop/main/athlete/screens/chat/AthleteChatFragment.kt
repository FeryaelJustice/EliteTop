package com.solucionescreativasdemallorca.elitetop.main.athlete.screens.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.solucionescreativasdemallorca.elitetop.R
import com.solucionescreativasdemallorca.elitetop.base.BaseFragment
import com.solucionescreativasdemallorca.elitetop.main.athlete.AthleteActivity
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.entrenamiento.AthleteEntrenamientoFragment

class AthleteChatFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(
            R.layout.fragment_athlete_chat,
            container, false
        )
        val toolbarBack: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_back)
        toolbarBack.visibility = View.VISIBLE
        toolbarBack.setOnClickListener {
            (activity as AthleteActivity).replaceFragment(
                R.id.athlete_fragment_container,
                AthleteEntrenamientoFragment(),
                "AthleteEntrenamientoFragment"
            )
        }
        val toolbarTitle: TextView = requireActivity().findViewById(R.id.athlete_toolbar_appname)
        toolbarTitle.visibility = View.GONE
        val toolbarChat: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_chat)
        toolbarChat.visibility = View.GONE
        val toolbarMenu: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_menu)
        toolbarMenu.visibility = View.GONE
        return rootView
    }
}