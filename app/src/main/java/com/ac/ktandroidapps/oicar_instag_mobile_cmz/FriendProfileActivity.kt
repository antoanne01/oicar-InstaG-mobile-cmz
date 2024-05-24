package com.ac.ktandroidapps.oicar_instag_mobile_cmz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.adapters.ViewPagerAdapter
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.ActivityFriendProfileBinding
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.fragments.ProfileFragment

class FriendProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFriendProfileBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra("userEmail")
    }
}