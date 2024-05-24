package com.ac.ktandroidapps.oicar_instag_mobile_cmz

import android.content.Intent
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

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            startActivity(Intent(this@FriendProfileActivity, HomeActivity::class.java))
            finish()
        }

        val userId = intent.getStringExtra("userEmail")
        loadProfileFragment(userId)
    }

    private fun loadProfileFragment(userEmail: String?) {
        val fragment = ProfileFragment().apply {
            arguments = Bundle().apply {
                putString("userEmail", userEmail)
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}