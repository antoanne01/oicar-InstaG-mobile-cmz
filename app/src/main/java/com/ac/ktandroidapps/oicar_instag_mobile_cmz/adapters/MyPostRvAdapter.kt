package com.ac.ktandroidapps.oicar_instag_mobile_cmz.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.MyPostRvDesignBinding
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.Post
import com.squareup.picasso.Picasso

class MyPostRvAdapter(var context: Context, var postList: List<Post>) : RecyclerView.Adapter<MyPostRvAdapter.ViewHolder>() {

    inner class ViewHolder(var binding : MyPostRvDesignBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = MyPostRvDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.get().load(postList[position].postUrl).into(holder.binding.ivPostImage)
    }
}