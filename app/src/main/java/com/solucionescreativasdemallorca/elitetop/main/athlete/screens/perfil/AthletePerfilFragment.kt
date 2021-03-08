package com.solucionescreativasdemallorca.elitetop.main.athlete.screens.perfil

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.solucionescreativasdemallorca.elitetop.R
import com.solucionescreativasdemallorca.elitetop.base.BaseFragment

class AthletePerfilFragment : BaseFragment() {

    private lateinit var userId: String

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var storageReference: StorageReference

    private lateinit var firebaseUser: FirebaseUser

    private lateinit var profileImage: ImageView
    private var imageUri: Uri? = null
    private lateinit var saveChangesBtn: Button

    companion object {
        fun GALLERY_REQUEST_CODE(): Int = 1000
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(
            R.layout.fragment_athlete_perfil,
            container, false
        )
        val toolbarBack: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_back)
        toolbarBack.visibility = View.GONE
        val toolbarTitle: TextView = requireActivity().findViewById(R.id.athlete_toolbar_appname)
        toolbarTitle.visibility = View.VISIBLE
        val toolbarChat: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_chat)
        toolbarChat.visibility = View.GONE
        val toolbarMenu: ImageView = requireActivity().findViewById(R.id.athlete_toolbar_menu)
        toolbarMenu.visibility = View.VISIBLE

        // Instantiate Firebase
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        storageReference = firebaseStorage.reference


        // Get data
        firebaseAuth.let {
            firebaseUser = it.currentUser!!
            userId = firebaseUser.uid

            firebaseFirestore.let { firebaseFirestore ->
                val document: DocumentReference =
                    firebaseFirestore.collection("users").document(userId)
                document.addSnapshotListener { snapshot, _ ->
                    if (snapshot != null && snapshot.exists()) {
                        val nickname: EditText =
                            rootView.findViewById(R.id.editprofile_form_nickname_material_text)
                        val password: EditText =
                            rootView.findViewById(R.id.editprofile_form_password_material_text)
                        val phoneNumber: EditText =
                            rootView.findViewById(R.id.editprofile_form_phone_material_text)
                        val accountType: EditText =
                            rootView.findViewById(R.id.editprofile_form_accounttype_material_dropdown)

                        nickname.setText(snapshot.getString("nickname"))
                        password.setText(snapshot.getString("password"))
                        phoneNumber.setText(snapshot.getString("phone"))
                        accountType.setText(snapshot.getString("accountType"))

                    }
                }
            }

            val profileRef = storageReference.child("users/$userId/profilepicture.jpg")
            profileRef.downloadUrl.addOnSuccessListener {
                profileImage.setImageURI(it)
            }
        }

        // On Clicks
        profileImage = rootView.findViewById(R.id.editprofile_form_profilepicture)
        profileImage.setOnClickListener {
            pickImage()
        }
        saveChangesBtn = rootView.findViewById(R.id.editprofile_form_btn)
        saveChangesBtn.setOnClickListener {
            imageUri?.let { it1 -> uploadImageToFirebase(it1) }
        }

        return rootView
    }

    private fun pickImage() {
        val openGalleryIntent: Intent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        openGalleryIntent.type = "image/*"
        startActivityForResult(openGalleryIntent, AthletePerfilFragment.GALLERY_REQUEST_CODE())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AthletePerfilFragment.GALLERY_REQUEST_CODE()) {
            if (resultCode == Activity.RESULT_OK) {
                imageUri = data?.data
                imageUri?.let {
                    profileImage.setImageURI(it)
                }
            }
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri) {
        val fileRef: StorageReference =
            storageReference.child("users/$userId/profilepicture.jpg")
        fileRef.putFile(imageUri).addOnSuccessListener {
            showMessage("Image Uploaded.")
        }.addOnFailureListener {
            showMessage("Failed to upload image.")
        }
    }
}