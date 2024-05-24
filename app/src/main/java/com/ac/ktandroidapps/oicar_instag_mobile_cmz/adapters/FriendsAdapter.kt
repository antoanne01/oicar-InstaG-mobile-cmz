package com.ac.ktandroidapps.oicar_instag_mobile_cmz.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.FriendProfileActivity
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.R
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.FriendsListRvBinding
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.User
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.USER_NODE
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class FriendsAdapter(val context: Context, private val userList : ArrayList<User>) :RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: FriendsListRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = FriendsListRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]

        Glide.with(context).load(userList[position].image).placeholder(R.drawable.user_profile).into(holder.binding.ivFriendImage)
        holder.binding.tvFriendName.text = userList[position].username

        holder.binding.root.setOnClickListener {
            val intent = Intent(context, FriendProfileActivity::class.java)
            intent.putExtra("userEmail", user.email)
            context.startActivity(intent)
        }
    }


}