package com.ac.ktandroidapps.oicar_instag_mobile_cmz.Post

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.HomeActivity
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.ActivityReelsBinding
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.Reel
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.User
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.REEL
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.REEL_FOLDER
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.USER_NODE
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.uploadVideo
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.File

class ReelsActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityReelsBinding.inflate(layoutInflater)
    }

    private lateinit var videoUrl : String
    private lateinit var progressDialog : ProgressDialog

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){
        uri ->
        uri?.let {
            val thumbnail = getVideoThumbnail(it)
            binding.ivUploadReels.setImageBitmap(thumbnail)

            uploadVideo(uri, REEL_FOLDER, progressDialog){
                url ->
                if(url != null){
                    videoUrl = url
                }
            }
        }
    }

    private fun getVideoThumbnail(videoUri: Uri): Bitmap? {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        return try {
            mediaMetadataRetriever.setDataSource(this, videoUri)
            mediaMetadataRetriever.getFrameAtTime(-1)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            mediaMetadataRetriever.release()
        }
    }

    private fun getVideoThumbnailFromUrl(videoUrl: String) {
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(videoUrl)
        val localFile = File.createTempFile("video", "mp4")

        storageReference.getFile(localFile).addOnSuccessListener {
            val videoUri = Uri.fromFile(localFile)
            val thumbnail = getVideoThumbnail(videoUri)
            binding.ivUploadReels.setImageBitmap(thumbnail)
        }.addOnFailureListener {
            it.printStackTrace()
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

        val reelUrl = intent.getStringExtra("reelUrl")
        val caption = intent.getStringExtra("caption")
        val documentId = intent.getStringExtra("documentId")

        if (reelUrl != null) {
            try{
                getVideoThumbnailFromUrl(reelUrl)
            }
            catch(e:Exception){
                e.printStackTrace()
            }
        }
        binding.tvReelCaptionText.setText(caption)

        binding.btnSelectReel.setOnClickListener {
            launcher.launch("video/*")
        }

        binding.btnCancel.setOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }

        binding.btnPublishReel.setOnClickListener {

            val userId = Firebase.auth.currentUser?.uid
            Firebase.firestore.collection(USER_NODE).document(userId!!).get().addOnSuccessListener {document ->
                val user = document.toObject<User>()
                val caption = binding.tvReelCaptionText.text.toString()

                if(reelUrl == null && documentId == null){
                    val reelId = Firebase.firestore.collection(REEL).document().id
                    val reel = Reel(
                        caption = binding.tvReelCaptionText.text.toString(),
                        reelUrl = videoUrl,
                        time = System.currentTimeMillis().toString(),
                        uid = userId,
                        user = user,
                        documentId = reelId
                    )
                    Firebase.firestore.collection(REEL).document(reelId).set(reel).addOnSuccessListener {
                        Firebase.firestore.collection(USER_NODE).document(userId)
                            .collection("Reel").document(reelId).set(reel).addOnSuccessListener {
                                startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
                                finish()
                            }
                    }
                }
                else{
                    val existingReelId = documentId ?: return@addOnSuccessListener
                    val updateData = mapOf(
                        "reelUrl" to (reelUrl),
                        "caption" to caption)

                    Firebase.firestore.collection(REEL).document(existingReelId).update(updateData).addOnSuccessListener {
                        Firebase.firestore.collection(USER_NODE).document(userId)
                            .collection("Reel").document(existingReelId).update(updateData).addOnSuccessListener {
                                startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
                                finish()
                            }
                    }
                }
            }
        }
    }
}