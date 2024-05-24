package com.ac.ktandroidapps.oicar_instag_mobile_cmz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.ActivityHelpBinding

class HelpActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {

            val intent = Intent(this@HelpActivity, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}