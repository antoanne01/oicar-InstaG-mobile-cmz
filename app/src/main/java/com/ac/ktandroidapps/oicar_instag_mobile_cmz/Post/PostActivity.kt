package com.ac.ktandroidapps.oicar_instag_mobile_cmz.Post

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

        binding.ivSelectImage.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.btnCancel.setOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }

        binding.btnPost.setOnClickListener {

            val userId = Firebase.auth.currentUser?.uid
            if(userId != null){
                Firebase.firestore.collection(USER_NODE).document(userId).get().addOnSuccessListener {
                    val user = it.toObject<User>()
                    if(user != null){
                        val post = Post(
                            postUrl = imageUrl,
                            caption = binding.tvCaption.text.toString(),
                            uid = userId,
                            time = System.currentTimeMillis().toString(),
                            user = user)

                        val postId = Firebase.firestore.collection(POST).document().id

                        Firebase.firestore.collection(POST).document(postId).set(post).addOnSuccessListener {
                            Firebase.firestore.collection(USER_NODE).document(userId)
                                .collection("Post").document(postId).set(post).addOnSuccessListener {
                                    startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                                    finish()
                                }
                        }
                    }
                }
            }
        }
    }
}