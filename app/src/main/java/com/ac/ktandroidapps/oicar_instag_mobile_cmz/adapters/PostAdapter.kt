package com.ac.ktandroidapps.oicar_instag_mobile_cmz.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.R
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.databinding.PostRvBinding
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.Post
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.model.User
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.utils.USER_NODE
import com.bumptech.glide.Glide
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class PostAdapter(val context: Context, val postList : ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.MyHolder>() {

    inner class MyHolder(var binding : PostRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var binding = PostRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try {
            Firebase.firestore.collection(USER_NODE).document(postList[position].uid!!).get().addOnSuccessListener {
                var user = it.toObject<User>()
                Glide.with(context).load(user!!.image).placeholder(R.drawable.user_profile).into(holder.binding.profileImage)
                holder.binding.tvName.text = user.username
            }
            Glide.with(context).load(postList[position].postUrl).placeholder(R.drawable.loading).into(holder.binding.ivPostImageHomePage)

            try {
                var txt : String = TimeAgo.using(postList[position].time!!.toLong())
                holder.binding.tvBio.text = txt
            }
            catch (e : Exception){
                holder.binding.tvBio.text = ""
            }

            holder.binding.tvCaptionHomePage.text = postList[position].caption

            // LIKE WITHOUT REVERTING

//            holder.binding.ivLike.setOnClickListener {
//                holder.binding.ivLike.setImageResource(R.drawable.heart_like)
//            }

            holder.binding.ivLike.setOnClickListener {
                val isLiked = holder.binding.ivLike.tag as? Boolean ?: false
                if (isLiked){
                    holder.binding.ivLike.setImageResource(R.drawable.like)
                    holder.binding.ivLike.tag = false
                }
                else{
                    holder.binding.ivLike.setImageResource(R.drawable.heart_like)
                    holder.binding.ivLike.tag = true
                }
            }

            holder.binding.ivShare.setOnClickListener {
                var i = Intent(Intent.ACTION_SEND)
                i.type = "text/plain"
                i.putExtra(Intent.EXTRA_TEXT, postList[position].postUrl)
                context.startActivity(i)
            }
        }
        catch (e : Exception){
            e.printStackTrace()
        }
    }
}