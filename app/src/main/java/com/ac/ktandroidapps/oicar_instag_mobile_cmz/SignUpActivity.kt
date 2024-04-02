package com.ac.ktandroidapps.oicar_instag_mobile_cmz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.ActivitySignUpBinding
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.User
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.USER_PROFILE_FOLDER
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.uploadImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso
import org.mindrot.jbcrypt.BCrypt

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val firebaseAuth : FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val firestore : FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private lateinit var user : User

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){
        uri ->
        uri.let {
            uploadImage(uri!!, USER_PROFILE_FOLDER){
                if(it == null){
                    Toast.makeText(this@SignUpActivity, "Choose image", Toast.LENGTH_SHORT).show()
                }
                else{
                    user.image = it
                    binding.profileImage.setImageURI(uri)
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = User()

        if(intent.hasExtra("MODE")){
            if(intent.getIntExtra("MODE", -1) == 1){

                binding.btnRegister.text = "Update profile"
                binding.tvLoginRedirect.text = ""

                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                    .addOnSuccessListener {

                        user = it.toObject<User>()!!
                        if(!user.image.isNullOrEmpty()){
                            Picasso.get().load(user.image).into(binding.profileImage)
                        }
                        binding.tvUsername.setText(user.username)
                        binding.tvEmail.setText(user.email)

                    }
            }
        }


        binding.btnRegister.setOnClickListener {

            val username = binding.tvUsername.text.toString()
            val email = binding.tvEmail.text.toString()
            val password = binding.tvPassword.text.toString()

            if(intent.hasExtra("MODE")){
                if(intent.getIntExtra("MODE", -1) == 1){

                    binding.btnRegister.text = "Update profile"
                    binding.tvLoginRedirect.text = ""

                    Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid)
                        .set(user).addOnSuccessListener {
                            startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                            finish()
                        }
                }
            }
            else{

            }

            if(username.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Please enter correct credentials", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                try {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                                result ->
                            if(result.isSuccessful){
                                Toast.makeText(this@SignUpActivity,
                                    "Successfull registration", Toast.LENGTH_SHORT).show()

                                val user = User(username, email)

                                firestore.collection(USER_NODE)
                                    .document(Firebase.auth.currentUser!!.uid).set(user)
                                    .addOnSuccessListener {
                                        startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                                        finish()
                                    }
                            }
                            else{
                                Toast.makeText(this@SignUpActivity, "Registration failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
                catch(e : Exception){
                    Log.e("SignUpError", "Registration failed: ", e)
                }

            }
        }

        binding.ivAddImage.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.tvLoginRedirect.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            finish()
        }
    }
}