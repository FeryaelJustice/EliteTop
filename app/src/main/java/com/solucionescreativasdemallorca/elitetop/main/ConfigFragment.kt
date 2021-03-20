package com.solucionescreativasdemallorca.elitetop.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.solucionescreativasdemallorca.elitetop.R
import com.solucionescreativasdemallorca.elitetop.base.BaseActivity
import com.solucionescreativasdemallorca.elitetop.base.BaseFragment
import com.solucionescreativasdemallorca.elitetop.dataclass.ConfigItem
import com.solucionescreativasdemallorca.elitetop.login.LoginActivity
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.entrenamiento.AthleteEntrenamientoFragment

class ConfigFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(
            R.layout.fragment_config,
            container, false
        )
        val toolbarBack: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_back)
        toolbarBack.visibility = View.VISIBLE
        toolbarBack.setOnClickListener {
            (activity as BaseActivity).replaceFragment(
                R.id.athlete_fragment_container,
                AthleteEntrenamientoFragment(),
                "AthleteEntrenamientoFragment",
                arguments
            )
        }
        val toolbarTitle: TextView = requireActivity().findViewById(R.id.athlete_toolbar_appname)
        toolbarTitle.visibility = View.GONE

        val toolbarChat: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_chat)
        toolbarChat.visibility = View.GONE
        val toolbarMenu: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_menu)
        toolbarMenu.visibility = View.GONE

        val listObjects = mutableListOf<ConfigItem>()
        listObjects.add(ConfigItem(R.drawable.ic_exit, "Cerrar sesión"))
        listObjects.add(ConfigItem(R.drawable.ic_person, "Perfil"))

        val listView: ListView = rootView.findViewById(R.id.config_list)
        val adapter = context?.let { ConfigListAdapter(it, listObjects) }
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val item: ConfigItem = listObjects[position]
            if (item.title == "Perfil") {
                // activity?.startActivity(Intent(activity, RecoverPasswordActivity::class.java))
            } else if (item.title == "Cerrar sesión") {
                activity?.let {
                    if (signOut()) {
                        val intent = Intent(it, LoginActivity::class.java)
                        it.startActivity(intent)
                        it.finish()
                    }
                }
            }
        }

        return rootView
    }

    private class ConfigListAdapter(
        private val mContext: Context,
        private val list: List<ConfigItem>
    ) :
        ArrayAdapter<ConfigItem>(mContext, 0, list) {

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val layout = LayoutInflater.from(mContext).inflate(R.layout.config_item, parent, false)

            val listItem = list[position]

            val image: ImageView = layout.findViewById(R.id.config_item_image)
            val title: TextView = layout.findViewById(R.id.config_item_title)

            image.setImageResource(listItem.imgId)
            title.text = listItem.title

            return layout
        }
    }

}