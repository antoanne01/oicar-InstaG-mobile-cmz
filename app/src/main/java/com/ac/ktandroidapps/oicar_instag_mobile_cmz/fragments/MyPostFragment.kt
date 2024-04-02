package com.ac.ktandroidapps.oicar_instag_mobile_cmz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.R
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.adapters.MyPostRvAdapter
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.FragmentMyPostBinding
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.Post
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.POST
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class MyPostFragment : Fragment() {

    private lateinit var binding : FragmentMyPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyPostBinding.inflate(layoutInflater, container, false)

        var postList = ArrayList<Post>()
        val adapter = MyPostRvAdapter(requireContext(), postList)

        binding.rvPost.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rvPost.adapter = adapter

        var currentUserId = Firebase.auth.currentUser?.uid
        if(currentUserId != null){
            Firebase.firestore.collection(USER_NODE).document(currentUserId).collection(POST).get().addOnSuccessListener {
                val tempList = it.mapNotNull {
                    it.toObject<Post>()
                }
                postList.clear()
                postList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }
        }

        return binding.root
    }

    companion object {

    }
}