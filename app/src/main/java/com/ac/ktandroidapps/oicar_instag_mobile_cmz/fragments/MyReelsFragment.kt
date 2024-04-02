package com.ac.ktandroidapps.oicar_instag_mobile_cmz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.R
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.adapters.MyReelRvAdapter
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.FragmentMyReelsBinding
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.Reel
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.REEL
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class MyReelsFragment : Fragment() {

    private lateinit var binding : FragmentMyReelsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyReelsBinding.inflate(layoutInflater, container, false)

        var reelList = ArrayList<Reel>()
        var adapter = MyReelRvAdapter(requireContext(), reelList)

        binding.rvReel.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rvReel.adapter = adapter

        val currentUserId = Firebase.auth.currentUser?.uid
        Firebase.firestore.collection(USER_NODE).document(currentUserId!!).collection(REEL).get().addOnSuccessListener {
            val tempReelList = it.mapNotNull {
                it.toObject<Reel>()
            }
            reelList.clear()
            reelList.addAll(tempReelList)
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    companion object {

    }
}