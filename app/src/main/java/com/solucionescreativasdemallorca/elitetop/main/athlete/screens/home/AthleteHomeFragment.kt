package com.solucionescreativasdemallorca.elitetop.main.athlete.screens.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.solucionescreativasdemallorca.elitetop.R
import com.solucionescreativasdemallorca.elitetop.base.BaseFragment
import com.solucionescreativasdemallorca.elitetop.dataclass.WeatherInstance
import com.solucionescreativasdemallorca.elitetop.util.Utils
import org.json.JSONException

class AthleteHomeFragment : BaseFragment() {
    
    private var requestQueue: RequestQueue? = null
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(
            R.layout.fragment_athlete_home,
            container, false
        )
        val toolbarBack: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_back)
        toolbarBack.visibility = View.GONE
        val toolbarTitle: TextView = requireActivity().findViewById(R.id.athlete_toolbar_appname)
        toolbarTitle.visibility = View.VISIBLE
        val toolbarScreenName: TextView =
            requireActivity().findViewById(R.id.athlete_toolbar_screenname)
        toolbarScreenName.visibility = View.GONE
        val toolbarChat: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_chat)
        toolbarChat.visibility = View.VISIBLE
        val toolbarMenu: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_menu)
        toolbarMenu.visibility = View.GONE
        
        val apiUrl = Utils.apiURL()
        requestQueue = Volley.newRequestQueue(context)
        val request = JsonObjectRequest(Request.Method.GET, apiUrl, null, { response ->
            try {
                Log.d("response", response.toString())
                
                val city = rootView.findViewById<TextView>(R.id.widget_weather_city)
                val temperature = rootView.findViewById<TextView>(R.id.widget_weather_temperature)
                val state = rootView.findViewById<TextView>(R.id.widget_weather_state)
                
                try {
                    val gson = Gson()
                    // Example: val weather = gson.toJson(WeatherInstance("Palma","20ºC","Cloudy"))
                    val weatherInstance: WeatherInstance =
                        gson.fromJson(response.toString(), WeatherInstance::class.java)
                    
                    city.text = weatherInstance.city
                    temperature.text = weatherInstance.temperature
                    state.text = weatherInstance.state
                } catch (e: Exception) {
                    Log.d("error", e.toString())
                }
                
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error -> error.printStackTrace() })
        requestQueue?.add(request)
        
        return rootView
    }
}