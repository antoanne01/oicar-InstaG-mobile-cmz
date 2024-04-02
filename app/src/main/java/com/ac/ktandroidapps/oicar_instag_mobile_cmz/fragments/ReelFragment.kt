package com.ac.ktandroidapps.oicar_instag_mobile_cmz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.R
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.adapters.ReelAdapter
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.FragmentReelBinding
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.Reel
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.REEL
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelFragment : Fragment() {

    private lateinit var binding : FragmentReelBinding
    private lateinit var adapter : ReelAdapter
    private var reelList = ArrayList<Reel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReelBinding.inflate(layoutInflater, container, false)

        adapter = ReelAdapter(requireContext(), reelList)
        binding.vpReels.adapter = adapter

        Firebase.firestore.collection(REEL).get().addOnSuccessListener {
            var tempArray = ArrayList<Reel>()
            reelList.clear()

            for(i in it.documents){
                i.toObject<Reel>()?.let {reel ->
                    tempArray.add(reel)
                }
//                val reel : Reel = i.toObject<Reel>()!!
//                tempArray.add(reel)
            }
            reelList.addAll(tempArray)
            reelList.reverse()
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    companion object {

    }
}