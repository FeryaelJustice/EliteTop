package com.solucionescreativasdemallorca.elitetop.main.athlete

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.solucionescreativasdemallorca.elitetop.R
import com.solucionescreativasdemallorca.elitetop.base.BaseActivity
import com.solucionescreativasdemallorca.elitetop.dataclass.User
import com.solucionescreativasdemallorca.elitetop.main.ConfigFragment
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.anadirevento.AthleteAnadirEventoFragment
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.chat.AthleteChatFragment
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.dieta.AthleteDietaFragment
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.entrenamiento.AthleteEntrenamientoFragment
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.home.AthleteHomeFragment
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.perfil.AthletePerfilFragment
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.resultados.AthleteResultadosFragment

class AthleteActivity : BaseActivity() {

    private var bundle: Bundle? = null

    // FIREBASE

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    // FIREBASE USER DATA

    private lateinit var userId: String
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var user: User
    private lateinit var imageUri: Uri

    // ACTIVITY ELEMENTS

    // Main Layout
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    // Nav View
    private lateinit var navigationView: NavigationView
    lateinit var navigationHeaderView: View

    // Bottom Navigation View
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_athlete)

        firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.let {
            firebaseUser = it.currentUser!!
            userId = firebaseUser.uid

            bundle = Bundle()
            bundle?.putString("userId", userId)

            // Instantiate activity elements
            val appName: TextView = findViewById(R.id.athlete_toolbar_appname)
            val chatNavigation: ImageView = findViewById(R.id.athlete_toolbar_chat)
            val profileMenu: ImageView = findViewById(R.id.athlete_toolbar_menu)
            val config: TextView = findViewById(R.id.athlete_navigation_config)
            drawerLayout = findViewById(R.id.athlete_drawerlayout)
            navigationView = findViewById(R.id.athlete_navigation_profile)
            navigationHeaderView = navigationView.getHeaderView(0)
            toggle = ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.nav_drawer_start,
                R.string.nav_drawer_close
            )
            bottomNavigation = findViewById(R.id.athlete_bottom_nav_view)

            // On Clicks
            appName.setOnClickListener {
                replaceFragment(
                    R.id.athlete_fragment_container,
                    AthleteHomeFragment(),
                    "AthleteHomeFragment",
                    bundle,
                )
            }
            chatNavigation.setOnClickListener {
                replaceFragment(
                    R.id.athlete_fragment_container,
                    AthleteChatFragment(),
                    "AthleteChatFragment",
                    bundle,
                )
            }
            profileMenu.setOnClickListener {
                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.closeDrawer(GravityCompat.END)
                } else {
                    drawerLayout.openDrawer(GravityCompat.END)
                }
            }
            config.setOnClickListener {
                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.closeDrawer(GravityCompat.END)
                    replaceFragment(
                        R.id.athlete_fragment_container,
                        ConfigFragment(),
                        "ConfigFragment", bundle,
                    )
                }
            }
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
            bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.bottom_nav_menu_athlete_item_entrenamiento -> {
                        replaceFragment(
                            R.id.athlete_fragment_container,
                            AthleteEntrenamientoFragment(),
                            "AthleteEntrenamientoFragment",
                            bundle,
                        )
                        true
                    }
                    R.id.bottom_nav_menu_athlete_item_dieta -> {
                        replaceFragment(
                            R.id.athlete_fragment_container,
                            AthleteDietaFragment(),
                            "AthleteDietaFragment",
                            bundle,
                        )
                        true
                    }
                    R.id.bottom_nav_menu_athlete_item_anadirevento -> {
                        replaceFragment(
                            R.id.athlete_fragment_container,
                            AthleteAnadirEventoFragment(),
                            "AthleteAnadirEventoFragment",
                            bundle,
                        )
                        true
                    }
                    R.id.bottom_nav_menu_athlete_item_resultados -> {
                        replaceFragment(
                            R.id.athlete_fragment_container,
                            AthleteResultadosFragment(),
                            "AthleteResultadosFragment",
                            bundle,
                        )
                        true
                    }
                    R.id.bottom_nav_menu_athlete_item_perfil -> {
                        replaceFragment(
                            R.id.athlete_fragment_container,
                            AthletePerfilFragment(),
                            "AthletePerfilFragment",
                            bundle,
                        )
                        true
                    }
                    else -> false
                }
            }

            // Default select one tab on bottom navigation
            // bottomNavigation.selectedItemId = R.id.bottom_nav_menu_athlete_item_entrenamiento

            showMessage(bundle?.getString("userId").toString())

            // Select home as default
            addFragment(
                R.id.athlete_fragment_container,
                AthleteHomeFragment(),
                "AthleteHomeFragment",
                bundle
            )

            // Set data
            setData()

        }
    }

    private fun setData() {
        // Instantiate Firebase
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integración de Firebase completa")
        firebaseAnalytics.logEvent("InitScreen", bundle)
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        storageReference = firebaseStorage.reference


        // Get data
        firebaseFirestore.let { firebaseFirestore ->
            val document: DocumentReference =
                firebaseFirestore.collection("users").document(userId)
            document.addSnapshotListener { snapshot, _ ->
                if (snapshot != null && snapshot.exists()) {
                    snapshot.let {
                        user = User(
                            snapshot.getString("email")!!,
                            snapshot.getString("nickname")!!,
                            snapshot.getString("password")!!,
                            snapshot.getString("phone")!!,
                            snapshot.getString("accountType")!!,
                            snapshot.getString("name")!!,
                            snapshot.getString("surnames")!!,
                            snapshot.getString("birth")!!,
                            snapshot.getString("genre")!!,
                            snapshot.getString("nationality")!!,
                            snapshot.getString("sports")!!
                        )

                        if (::navigationHeaderView.isInitialized) {
                            navigationHeaderView.let {
                                val userNick: TextView =
                                    navigationHeaderView.findViewById(R.id.athlete_menu_navigation_user)
                                userNick.text = getString(R.string.user, user.nickname)
                            }
                        }
                    }
                }
            }
        }
        storageReference.let {
            val profileRef =
                storageReference.child("users/$userId/profilepicture.jpg")
            profileRef.downloadUrl.addOnSuccessListener {
                imageUri = it
                if (::navigationHeaderView.isInitialized) {
                    navigationHeaderView.let {
                        val imageProfile: ImageView =
                            navigationHeaderView.findViewById(R.id.athlete_menu_navigation_header)
                        Glide.with(this).load(imageUri).into(imageProfile)
                    }
                }
            }
        }

    }

    // Close navigation drawer on back pressed
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            val displayedFragment = supportFragmentManager.findFragmentByTag("ConfigFragment")
            if (displayedFragment != null && displayedFragment.isVisible) {
                //finish()
                super.onBackPressed()
            } else {
                super.onBackPressed()
            }
        }
    }
}