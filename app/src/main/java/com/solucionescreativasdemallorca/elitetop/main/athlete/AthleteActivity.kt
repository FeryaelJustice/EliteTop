package com.solucionescreativasdemallorca.elitetop.main.athlete

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.solucionescreativasdemallorca.elitetop.R
import com.solucionescreativasdemallorca.elitetop.base.BaseActivity
import com.solucionescreativasdemallorca.elitetop.main.ConfigFragment
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.anadirevento.AthleteAnadirEventoFragment
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.chat.AthleteChatFragment
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.dieta.AthleteDietaFragment
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.entrenamiento.AthleteEntrenamientoFragment
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.perfil.AthletePerfilFragment
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.resultados.AthleteResultadosFragment

class AthleteActivity : BaseActivity() {

    private var firebaseUser: FirebaseUser? = null

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_athlete)

        firebaseUser = FirebaseAuth.getInstance().currentUser

        // APP BAR NAVIGATION (TOP)

        val chatNavigation: ImageView = findViewById(R.id.athlete_toolbar_chat)
        chatNavigation.setOnClickListener {
            replaceFragment(
                R.id.athlete_fragment_container,
                AthleteChatFragment(),
                "AthleteChatFragment"
            )
        }

        // Navigation Drawer
        drawerLayout = findViewById(R.id.athlete_drawerlayout)
        navigationView = findViewById(R.id.athlete_navigation_profile)
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.nav_drawer_start,
            R.string.nav_drawer_close
        )
        // Open navigation by custom element
        val profileMenu: ImageView = findViewById(R.id.athlete_toolbar_menu)
        profileMenu.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                drawerLayout.openDrawer(GravityCompat.END)
            }
        }

        // Navigation Click
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.athlete_profile_navigation_item_perfil -> {
                    if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                        drawerLayout.closeDrawer(GravityCompat.END)
                    }
                    true
                }
                R.id.athlete_profile_navigation_item_training -> {
                    if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                        drawerLayout.closeDrawer(GravityCompat.END)
                    }
                    true
                }
                else -> false
            }
        }

        // Navigation click bottom (independent from onnavigationitemselectedlistener)
        val config: TextView = findViewById(R.id.athlete_navigation_config)
        config.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END)
                replaceFragment(
                    R.id.athlete_fragment_container,
                    ConfigFragment(),
                    "ConfigFragment"
                )
            }
        }

        // BOTTOM NAVIGATION

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
        // Default select one tab
        bottomNavigation.selectedItemId = R.id.bottom_nav_menu_athlete_item_entrenamiento
    }

    // Close navigation drawer on back pressed
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            val displayedFragment = supportFragmentManager.findFragmentByTag("ConfigFragment")
            if (displayedFragment != null && displayedFragment.isVisible) {
                finish()
            } else {
                super.onBackPressed()
            }
        }
    }
}