package com.ac.ktandroidapps.oicar_instag_mobile_cmz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.ActivityDeleteAccountBinding

class DeleteAccountActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDeleteAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {

            val intent = Intent(this@DeleteAccountActivity, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}