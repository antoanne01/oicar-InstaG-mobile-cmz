package com.ac.ktandroidapps.oicar_instag_mobile_cmz.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.Post.PostActivity
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.Post.ReelsActivity
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.MyReelsRvDesignBinding
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.Reel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.squareup.picasso.Picasso

class MyReelRvAdapter(val context: Context, val reelList: ArrayList<Reel>) : RecyclerView.Adapter<MyReelRvAdapter.ViewHolder>() {

    inner class ViewHolder(var binding : MyReelsRvDesignBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = MyReelsRvDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reel = reelList[position]

        Glide.with(context)
            .load(reelList[position].reelUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.ivReelVideo)

        holder.binding.root.setOnClickListener {
            val intent = Intent(context, ReelsActivity::class.java)
            intent.putExtra("reelUrl", reel.reelUrl)
            intent.putExtra("caption", reel.caption)
            intent.putExtra("documentId", reel.documentId)

            context.startActivity(intent)
        }
    }
}