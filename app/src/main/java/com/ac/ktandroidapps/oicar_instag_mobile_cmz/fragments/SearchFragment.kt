package com.ac.ktandroidapps.oicar_instag_mobile_cmz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.adapters.SearchAdapter
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.FragmentSearchBinding
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.User
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var adapter: SearchAdapter
    var userList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.rvSearch.layoutManager = LinearLayoutManager(requireContext())
        adapter = SearchAdapter(requireContext(), userList)
        binding.rvSearch.adapter = adapter

        var currentUserId = Firebase.auth.currentUser?.uid
        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {
            var tempList = ArrayList<User>()
            userList.clear()
            for(document in it.documents){
                if(document.id != currentUserId){
                    var user : User = document.toObject<User>()!!
                    tempList.add(user)
                }
            }
            userList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        binding.ibSearchButton.setOnClickListener {
            var text = binding.edSearch.text.toString()
            Firebase.firestore.collection(USER_NODE).whereEqualTo("username", text).get().addOnSuccessListener {
                var tempList = ArrayList<User>()
                userList.clear()
                if(!it.isEmpty){
                    for(i in it.documents){
                        if(i.id != currentUserId){
                            var user : User = i.toObject<User>()!!
                            tempList.add(user)
                        }
                    }
                }
                userList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }
        }

        return binding.root
    }

    companion object {

    }
}