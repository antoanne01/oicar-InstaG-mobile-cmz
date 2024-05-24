package com.ac.ktandroidapps.oicar_instag_mobile_cmz.Post

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.HomeActivity
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.ActivityPostBinding
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.Post
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.User
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.POST
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.POST_FOLDER
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.USER_NODE
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.uploadImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class PostActivity : AppCompatActivity() {

    val binding by lazy{
        ActivityPostBinding.inflate(layoutInflater)
    }

    var imageUrl : String? = null
    private var launcher = registerForActivityResult(ActivityResultContracts.GetContent()){
        uri ->
        uri?.let {
            uploadImage(uri, POST_FOLDER){
                url ->
                if(url != null){
                    binding.ivSelectImage.setImageURI(uri)
                    imageUrl = url
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener{
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }

        val postUrl = intent.getStringExtra("postUrl")
        val caption = intent.getStringExtra("caption")
        val documentId = intent.getStringExtra("documentId")

        if (postUrl != null) {
            Picasso.get().load(postUrl).into(binding.ivSelectImage)
        }
        binding.tvCaption.setText(caption)

        binding.ivSelectImage.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.btnCancel.setOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }

        binding.btnPost.setOnClickListener {

            val userId = Firebase.auth.currentUser?.uid
            Firebase.firestore.collection(USER_NODE).document(userId!!).get().addOnSuccessListener {document ->
                val user = document.toObject<User>()
                val captionText = binding.tvCaption.text.toString()

            if(postUrl == null && documentId == null){
                val postId = Firebase.firestore.collection(POST).document().id
                val post = Post(
                    postUrl = imageUrl,
                    caption = captionText,
                    uid = userId,
                    time = System.currentTimeMillis().toString(),
                    user = user,
                    documentId = postId
                )
                Firebase.firestore.collection(POST).document(postId).set(post).addOnSuccessListener {
                    Firebase.firestore.collection(USER_NODE).document(userId)
                        .collection("Post").document(postId).set(post).addOnSuccessListener {
                            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                            finish()
                        }
                    }
                }
                else{
                val existingPostId = documentId ?: return@addOnSuccessListener
                val updateData = mapOf(
                    "postUrl" to (imageUrl ?: postUrl),
                    "caption" to captionText
                    )

                Firebase.firestore.collection(POST).document(existingPostId).update(updateData).addOnSuccessListener {
                    Firebase.firestore.collection(USER_NODE).document(userId)
                        .collection("Post").document(existingPostId).update(updateData).addOnSuccessListener {
                            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                            finish()
                        }
                }
                }
            }
        }

        binding.btnDelete.setOnClickListener {
            if(postUrl == null && documentId == null){
                return@setOnClickListener
            }
            else{

                AlertDialog.Builder(this)
                    .setTitle("Confirm Deletion")
                    .setMessage("Are you sure you want to delete post? This action cannot be undone.")
                    .setPositiveButton("Yes") { dialog, which ->
                        deletePost(documentId)
                    }
                    .setNegativeButton("No") { dialog, which ->
                        dialog.dismiss()
                    }
                    .show()


            }
        }
    }

    private fun deletePost(documentId: String?) {
        Firebase.firestore.collection(POST).document(documentId!!).delete().addOnSuccessListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid)
                .collection("Post").document(documentId).delete().addOnSuccessListener {
                    startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                    finish()
                }
        }
    }


}