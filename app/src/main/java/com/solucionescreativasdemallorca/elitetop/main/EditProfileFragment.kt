package com.solucionescreativasdemallorca.elitetop.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
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
import com.solucionescreativasdemallorca.elitetop.main.athlete.AthleteActivity
import com.solucionescreativasdemallorca.elitetop.main.athlete.screens.perfil.AthletePerfilFragment

class EditProfileFragment : BaseFragment() {

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

    // User data form
    private lateinit var profileImage: ImageView
    private lateinit var nickname: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var accountType: AutoCompleteTextView

    // Button save form
    private lateinit var saveChangesBtn: Button

    companion object {
        fun gallery_request_code(): Int = 1000
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(
            R.layout.fragment_edit_profile,
            container, false
        )

        // App bar
        val toolbarBack: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_back)
        toolbarBack.visibility = View.VISIBLE
        toolbarBack.setOnClickListener {
            (activity as BaseActivity).replaceFragment(
                R.id.athlete_fragment_container,
                AthletePerfilFragment(),
                "AthletePerfilFragment"
            )
        }
        val toolbarTitle: TextView = requireActivity().findViewById(R.id.athlete_toolbar_appname)
        toolbarTitle.visibility = View.GONE
        val toolbarChat: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_chat)
        toolbarChat.visibility = View.GONE
        val toolbarMenu: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_menu)
        toolbarMenu.visibility = View.GONE

        // Instantiate fragment elements
        nickname = rootView.findViewById(R.id.editprofile_form_nickname_material_text)
        phoneNumber = rootView.findViewById(R.id.editprofile_form_phone_material_text)
        accountType = rootView.findViewById(R.id.editprofile_form_accounttype_material_dropdown)
        ArrayAdapter.createFromResource(
            rootView.context,
            R.array.accountType_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            accountType.setAdapter(adapter)
        }
        profileImage = rootView.findViewById(R.id.editprofile_form_profilepicture)
        saveChangesBtn = rootView.findViewById(R.id.editprofile_form_btn)

        // Instantiate Firebase and Get and Set Data
        initData()

        // On Clicks
        profileImage.setOnClickListener {
            pickImage()
        }
        saveChangesBtn.setOnClickListener {
            updateProfile()
        }

        return rootView
    }

    private fun initData() {
        // Instantiate Firebase
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        storageReference = firebaseStorage.reference

        firebaseAuth.let {
            firebaseUser = it.currentUser!!
            userId = firebaseUser.uid
            getAndSetProfileData()
        }
    }

    private fun getAndSetProfileData() {
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

                        nickname.setText(user.nickname)
                        phoneNumber.setText(user.phone)
                        resources.getStringArray(R.array.accountType_array)
                            .forEachIndexed { index, string ->
                                if (string == user.accountType) {
                                    accountType.setText(
                                        accountType.adapter.getItem(index).toString(),
                                        false
                                    )
                                }
                            }
                    }
                }
            }
        }

        storageReference.let {
            val profileRef = storageReference.child("users/$userId/profilepicture.jpg")
            profileRef.downloadUrl.addOnSuccessListener {
                imageUri = it

                context?.let { context ->
                    Glide.with(context).load(imageUri).into(profileImage)
                }
            }
        }
    }

    private fun pickImage() {
        val openGalleryIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        openGalleryIntent.type = "image/*"
        startActivityForResult(openGalleryIntent, gallery_request_code())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == gallery_request_code()) {
            if (resultCode == Activity.RESULT_OK) {
                imageUri = data?.data!!
                imageUri.let {
                    profileImage.setImageURI(it)
                }
            }
        }
    }

    private fun updateProfile() {
        firebaseFirestore.let { firebaseFirestore ->
            user.nickname = nickname.text.toString()
            user.phone = phoneNumber.text.toString()
            user.accountType = accountType.text.toString()

            firebaseFirestore.collection("users").document(userId).set(user)
                .addOnSuccessListener {
                    uploadImageToFirebase(imageUri)
                    showMessage("Profile updated.")
                }.addOnFailureListener {
                    showMessage("Failed to update the profile..")
                }
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri) {
        val fileRef: StorageReference =
            storageReference.child("users/$userId/profilepicture.jpg")
        fileRef.putFile(imageUri).addOnSuccessListener {
            this.imageUri = imageUri
            val imageProfile: ImageView =
                (activity as AthleteActivity).navigationHeaderView.findViewById(R.id.athlete_menu_navigation_header)
            Glide.with(this).load(imageUri).into(imageProfile)
        }
    }

}