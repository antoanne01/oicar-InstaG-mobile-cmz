package com.ac.ktandroidapps.oicar_instag_mobile_cmz.Post

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.HomeActivity
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.ActivityReelsBinding
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.Reel
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.User
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.POST
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.REEL
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.REEL_FOLDER
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.USER_NODE
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.uploadVideo
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelsActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityReelsBinding.inflate(layoutInflater)
    }

    private lateinit var videoUrl : String
    private lateinit var progressDialog : ProgressDialog

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){
        uri ->
        uri?.let {
            uploadVideo(uri, REEL_FOLDER, progressDialog){
                url ->
                if(url != null){
                    videoUrl = url
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)

        setSupportActionBar(binding.reelMaterialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.reelMaterialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }

        binding.btnSelectReel.setOnClickListener {
            launcher.launch("video/*")
        }

        binding.btnCancel.setOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }

        binding.btnPublishReel.setOnClickListener {

            val userId = Firebase.auth.currentUser?.uid
            if(userId != null){
                Firebase.firestore.collection(USER_NODE).document(userId).get().addOnSuccessListener {
                    val user = it.toObject<User>()
                    if(user != null){
                        val reel = Reel(
                            caption = binding.tvReelCaptionText.toString(),
                            reelUrl = videoUrl,
                            time = System.currentTimeMillis().toString(),
                            uid = userId,
                            user = user)

                        val reelId = Firebase.firestore.collection(REEL).document().id

                        Firebase.firestore.collection(REEL).document(reelId).set(reel).addOnSuccessListener {
                            Firebase.firestore.collection(USER_NODE).document(userId)
                                .collection(REEL).document(reelId).set(reel).addOnSuccessListener {
                                    startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
                                    finish()
                                }
                        }
                    }
                }
            }
        }
    }
}