package com.ac.ktandroidapps.oicar_instag_mobile_cmz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.ActivityLoginBinding
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.tvEmail.text.toString()
            val password = binding.tvPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                var user = User(email, password)
                FirebaseAuth.getInstance().signInWithEmailAndPassword(user.email!!, user.passwordHash!!)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this@LoginActivity, "Error occured", Toast.LENGTH_SHORT).show()
                            return@addOnCompleteListener
                        }
                    }
            }
        }
        binding.btnRegisterRedirect.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            finish()
        }
    }
}