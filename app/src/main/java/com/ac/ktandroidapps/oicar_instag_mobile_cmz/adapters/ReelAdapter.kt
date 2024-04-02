package com.ac.ktandroidapps.oicar_instag_mobile_cmz.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.R
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.ReelDesignBinding
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.Reel
import com.squareup.picasso.Picasso

class ReelAdapter(val context: Context, val reelList : ArrayList<Reel>) : RecyclerView.Adapter<ReelAdapter.ViewHolder>() {

    inner class ViewHolder(var binding : ReelDesignBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ReelDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(reelList[position].user?.image).placeholder(R.drawable.user_profile).into(holder.binding.ivReelProfileImage)
        holder.binding.tvReelCaption.setText(reelList[position].caption)
        holder.binding.vvReels.setVideoPath(reelList[position].reelUrl)

        holder.binding.vvReels.setOnPreparedListener {
            holder.binding.pbReels.visibility = View.GONE
            holder.binding.vvReels.start()
        }
    }
}