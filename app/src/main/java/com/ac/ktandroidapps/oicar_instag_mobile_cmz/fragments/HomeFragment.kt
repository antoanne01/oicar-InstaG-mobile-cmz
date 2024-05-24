package com.ac.ktandroidapps.oicar_instag_mobile_cmz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.R
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.adapters.FriendsAdapter
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.adapters.PostAdapter
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.FragmentHomeBinding
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.Post
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.User
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.FOLLOW
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.POST
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var adapter : PostAdapter
    private var postList = ArrayList<Post>()

    private var friendsList = ArrayList<User>()
    private lateinit var friendsAdapter : FriendsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fetchFriendsData()

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapter = PostAdapter(requireContext(), postList)
        binding.rvHomePosts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHomePosts.adapter = adapter

        friendsAdapter = FriendsAdapter(requireContext(), friendsList)
        binding.rvFriendsList.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = friendsAdapter
        }

        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar2)

        Firebase.firestore.collection(POST).get().addOnSuccessListener {
            var tempList = ArrayList<Post>()
            postList.clear()
            for(document in it.documents){
                var post : Post = document.toObject<Post>()!!
                tempList.add(post)
            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    private fun fetchFriendsData() {
        var currentUserId = Firebase.auth.currentUser?.uid

        Firebase.firestore.collection(USER_NODE).document(currentUserId!!)
            .collection(FOLLOW).get().addOnSuccessListener {documents ->
                friendsList.clear()
                for(document in documents){
                    val user = document.toObject<User>()
                    friendsList.add(user)
                }
                friendsAdapter.notifyDataSetChanged()
            }
    }

    companion object {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}