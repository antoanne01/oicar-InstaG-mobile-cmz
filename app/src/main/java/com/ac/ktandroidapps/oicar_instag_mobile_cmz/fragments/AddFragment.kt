package com.ac.ktandroidapps.oicar_instag_mobile_cmz.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.Post.PostActivity
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.Post.ReelsActivity
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.R
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.FragmentAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.llPost.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), PostActivity::class.java))
            activity?.finish()
        }

        binding.llReel.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), ReelsActivity::class.java))
            activity?.finish()
        }

        return binding.root
    }

    companion object {
    }
}