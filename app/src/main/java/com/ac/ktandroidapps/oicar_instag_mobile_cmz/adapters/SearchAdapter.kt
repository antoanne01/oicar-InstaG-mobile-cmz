package com.ac.ktandroidapps.oicar_instag_mobile_cmz.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.R
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.SearchRvBinding
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.User
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.FOLLOW
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.USER_NODE
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class SearchAdapter(var context: Context, var userList: ArrayList<User>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    inner class ViewHolder(var binding : SearchRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = SearchRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var isFollow = false
        var currentUser = Firebase.auth.currentUser?.uid
        val followedUser = userList[position]

        Glide.with(context).load(userList[position].image).placeholder(R.drawable.user_profile)
            .into(holder.binding.profileImage)
        holder.binding.tvProfileName.text = userList[position].username

        Firebase.firestore.collection(USER_NODE).document(currentUser!!).collection(FOLLOW)
            .whereEqualTo("email", userList[position].email)
            .get().addOnSuccessListener {
                if (it.documents.size == 0) {
                    isFollow = false
                } else {
                    holder.binding.btnFollow.text = "Unfollow"
                    isFollow = true
                }
            }

        holder.binding.btnFollow.setOnClickListener {

            if(currentUser != null){
                val followDocRef = Firebase.firestore.collection(USER_NODE).document(currentUser)
                    .collection(FOLLOW).document(followedUser.email.toString())

                if(isFollow){
                    followDocRef.delete().addOnSuccessListener {
                        holder.binding.btnFollow.text = "Follow"
                        isFollow = false
                    }
                }
                else{
                    followDocRef.set(followedUser).addOnSuccessListener {
                        holder.binding.btnFollow.text = "Unfollow"
                        isFollow = true
                    }
                }
            }
        }
    }
}