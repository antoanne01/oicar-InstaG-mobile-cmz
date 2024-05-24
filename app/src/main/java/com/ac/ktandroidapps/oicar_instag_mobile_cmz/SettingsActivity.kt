package com.ac.ktandroidapps.oicar_instag_mobile_cmz

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.ActivitySettingsBinding
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.fragments.ProfileFragment
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {

            val intent = Intent(this, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                putExtra("OPEN_FRAGMENT", R.id.profile)
            }
            startActivity(intent)
        }

        setupListeners()
    }

    private fun setupListeners() {
        binding.clSettingsFirst.setOnClickListener{
            navigateToSettingsOptions("HelpActivity")
        }

        binding.clSettingsSecond.setOnClickListener {
            navigateToSettingsOptions("AboutActivity")
        }

        binding.clSettingsThird.setOnClickListener {
            showDeleteConfirmationDialog()
        }
        binding.clSettingsFourthLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            navigateToLoginScreen()
        }
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Confirm Deletion")
            .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
            .setPositiveButton("Yes") { dialog, which ->
                deleteAccount()
            }
            .setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun deleteAccount() {

        val currentUserID = Firebase.auth.currentUser!!.uid
        val uniqueNumber = (10..9999).random()
        val newUsername = "JohnDoe"
        val newEmail = "$newUsername-$uniqueNumber"

        val firestore = FirebaseFirestore.getInstance()
        val userDocument = firestore.collection(USER_NODE).document(currentUserID)

        userDocument.get().addOnSuccessListener { documentSnapshot ->
            val currentEmail = documentSnapshot.getString("email") ?: ""
            val currentUsername = documentSnapshot.getString("username") ?: ""

            val updates = mapOf(
                "email" to newEmail,
                "originalMail" to currentEmail,
                "username" to newUsername,
                "originalUsername" to currentUsername
            )

            userDocument.update(updates).addOnSuccessListener {
                FirebaseAuth.getInstance().signOut()
                navigateToLoginScreen()
            }
        }.addOnFailureListener { e ->
            Log.e("FetchError", "Error occured: ", e)
        }
    }

    private fun navigateToLoginScreen() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToSettingsOptions(settingOption: String) {
        val activityMap = mapOf(
            "HelpActivity" to HelpActivity::class.java,
            "AboutActivity" to AboutActivity::class.java
        )

        val activityClass = activityMap[settingOption]
        activityClass?.let {
            val intent = Intent(this, it)
            startActivity(intent)
            finish()
        }
    }
}