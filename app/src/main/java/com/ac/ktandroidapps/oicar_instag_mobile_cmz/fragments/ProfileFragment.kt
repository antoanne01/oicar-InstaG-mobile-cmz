package com.ac.ktandroidapps.oicar_instag_mobile_cmz.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.R
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.SettingsActivity
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.SignUpActivity
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.adapters.MyPostRvAdapter
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.adapters.ViewPagerAdapter
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.FragmentProfileBinding
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.Post
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.User
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val userEmail = arguments?.getString("userEmail")
        initializeFragments(userEmail)

        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(activity, SignUpActivity::class.java)
            intent.putExtra("MODE", 1)
            startActivity(intent)
            activity?.finish()
        }

        binding.btnSettings.setOnClickListener {
            val intent = Intent(requireActivity(), SettingsActivity::class.java)
            startActivity(intent)
            requireActivity()?.finish()
        }

        return binding.root
    }

    private fun initializeFragments(userEmail: String?) {
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPagerAdapter.addFragment(MyPostFragment.newInstance(userEmail), "My Post")
        viewPagerAdapter.addFragment(MyReelsFragment.newInstance(userEmail), "My Reels")
        binding.viewPager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    companion object {
    }

    override fun onStart() {
        super.onStart()
        val userEmail = arguments?.getString("userEmail") ?: Firebase.auth.currentUser?.email
        fetchUserProfile(userEmail)
    }

    private fun fetchUserProfile(userEmail: String?) {
        userEmail?.let {
            Firebase.firestore.collection(USER_NODE).whereEqualTo("email", it).limit(1).get()
                .addOnSuccessListener { documents ->
                    if (documents.documents.isNotEmpty()) {
                        val user = documents.documents.first().toObject<User>()!!
                        updateUI(user)
                    }
                }
        }
    }

    private fun updateUI(user: User) {
        binding.tvName.text = user.username
        binding.tvBio.text = user.email
        if (!user.image.isNullOrEmpty()) {
            Picasso.get().load(user.image).into(binding.profileImage)
        }
    }
}