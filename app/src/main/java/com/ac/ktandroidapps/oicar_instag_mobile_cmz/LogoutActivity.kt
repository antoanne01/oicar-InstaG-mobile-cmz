package com.ac.ktandroidapps.oicar_instag_mobile_cmz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.ActivityLogoutBinding

class LogoutActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLogoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}