package com.solucionescreativasdemallorca.elitetop.main.athlete.screens.perfil

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.solucionescreativasdemallorca.elitetop.R
import com.solucionescreativasdemallorca.elitetop.base.BaseActivity
import com.solucionescreativasdemallorca.elitetop.base.BaseFragment
import com.solucionescreativasdemallorca.elitetop.dataclass.User
import com.solucionescreativasdemallorca.elitetop.main.EditProfileFragment

class AthletePerfilFragment : BaseFragment() {

    // FIREBASE USER DATA

    private lateinit var userId: String
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var user: User
    private lateinit var imageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(
            R.layout.fragment_athlete_perfil,
            container, false
        )

        showMessage(arguments?.getString("userId") ?: "")

        firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.let {
            firebaseUser = it.currentUser!!
            userId = firebaseUser.uid

            val userIdOwnerPetition: String =
                arguments?.getString("userId") ?: ""

            if (userId == userIdOwnerPetition) {
                initMyProfile(rootView)
            } else {
                initPublicProfile(rootView)
            }

        }
        return rootView
    }

    private fun initPublicProfile(rootView: View) {

    }

    private fun initMyProfile(rootView: View) {
        // App bar
        val toolbarBack: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_back)
        toolbarBack.visibility = View.GONE
        val toolbarTitle: TextView = requireActivity().findViewById(R.id.athlete_toolbar_appname)
        toolbarTitle.visibility = View.VISIBLE
        val toolbarChat: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_chat)
        toolbarChat.visibility = View.GONE
        val toolbarMenu: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_menu)
        toolbarMenu.visibility = View.VISIBLE

        // Instantiate fragment elements
        val editarPerfilBtn = rootView.findViewById(R.id.profile_editarPerfilBtn) as MaterialButton

        // On Clicks
        editarPerfilBtn.setOnClickListener {
            openEditProfile()
        }

        setProfileData(rootView)

    }

    private fun openEditProfile() {
        (activity as BaseActivity).replaceFragment(
            R.id.athlete_fragment_container,
            EditProfileFragment(),
            "EditProfileFragment",
            arguments
        )
    }

    private fun setProfileData(rootView: View) {
        // Instantiate Firebase
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

                        val name = rootView.findViewById(R.id.profile_name) as TextView
                        val nickname = rootView.findViewById(R.id.profile_nickname) as TextView
                        val accountType =
                            rootView.findViewById(R.id.profile_accountType) as TextView
                        name.text = user.name
                        nickname.text = user.nickname
                        accountType.text = user.accountType
                    }
                }
            }
        }

        storageReference.let {
            val profileRef = storageReference.child("users/$userId/profilepicture.jpg")
            profileRef.downloadUrl.addOnSuccessListener {
                imageUri = it

                context?.let { context ->
                    val profileImage = rootView.findViewById(R.id.profile_photo) as ImageView
                    Glide.with(context).load(imageUri).into(profileImage)
                }
            }
        }
    }
}