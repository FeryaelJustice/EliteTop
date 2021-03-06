package com.solucionescreativasdemallorca.elitetop.main.athlete

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.solucionescreativasdemallorca.elitetop.R
import com.solucionescreativasdemallorca.elitetop.base.BaseActivity
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.anadirevento.AthleteAnadirEventoFragment
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.dieta.AthleteDietaFragment
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.entrenamiento.AthleteEntrenamientoFragment
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.perfil.AthletePerfilFragment
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.resultados.AthleteResultadosFragment

class AthleteActivity : BaseActivity() {

    private var firebaseUser: FirebaseUser? = null
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_athlete)

        firebaseUser = FirebaseAuth.getInstance().currentUser

        /*val signOutButton: ImageView = findViewById(R.id.athlete_toolbarRightButton)
        signOutButton.setOnClickListener {
            if (signOut()) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }*/

        bottomNavigation = findViewById(R.id.athlete_bottom_nav_view)
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_nav_menu_athlete_item_entrenamiento -> {
                    replaceFragment(
                        R.id.athlete_fragment_container,
                        AthleteEntrenamientoFragment(),
                        "AthleteEntrenamientoFragment"
                    )
                    true
                }
                R.id.bottom_nav_menu_athlete_item_dieta -> {
                    replaceFragment(
                        R.id.athlete_fragment_container,
                        AthleteDietaFragment(),
                        "AthleteDietaFragment"
                    )
                    true
                }
                R.id.bottom_nav_menu_athlete_item_anadirevento -> {
                    replaceFragment(
                        R.id.athlete_fragment_container,
                        AthleteAnadirEventoFragment(),
                        "AthleteAnadirEventoFragment"
                    )
                    true
                }
                R.id.bottom_nav_menu_athlete_item_resultados -> {
                    replaceFragment(
                        R.id.athlete_fragment_container,
                        AthleteResultadosFragment(),
                        "AthleteResultadosFragment"
                    )
                    true
                }
                R.id.bottom_nav_menu_athlete_item_perfil -> {
                    replaceFragment(
                        R.id.athlete_fragment_container,
                        AthletePerfilFragment(),
                        "AthletePerfilFragment"
                    )
                    true
                }
                else -> false
            }
        }
        bottomNavigation.selectedItemId = R.id.bottom_nav_menu_athlete_item_entrenamiento
    }

    private fun signOut(): Boolean {
        return try {
            FirebaseAuth.getInstance().signOut()
            true
        } catch (e: Exception) {
            false
        }
    }
}