package com.solucionescreativasdemallorca.elitetop.main.athlete.screens.resultados

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.solucionescreativasdemallorca.elitetop.R
import com.solucionescreativasdemallorca.elitetop.base.BaseFragment

class AthleteResultadosFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(
            R.layout.fragment_athlete_resultados,
            container, false
        )
        val toolbarBack: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_back)
        toolbarBack.visibility = View.GONE
        val toolbarTitle: TextView = requireActivity().findViewById(R.id.athlete_toolbar_appname)
        toolbarTitle.visibility = View.GONE
        val toolbarScreenName: TextView =
            requireActivity().findViewById(R.id.athlete_toolbar_screenname)
        toolbarScreenName.visibility = View.VISIBLE
        toolbarScreenName.text = resources.getString(R.string.bottom_nav_menu_item_resultados)
        val toolbarChat: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_chat)
        toolbarChat.visibility = View.GONE
        val toolbarMenu: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_menu)
        toolbarMenu.visibility = View.GONE
        return rootView
    }

}