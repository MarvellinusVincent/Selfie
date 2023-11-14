package com.example.selfieaday

import android.content.Context
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.selfieaday.databinding.PostItemBinding
import com.example.selfieaday.model.Post
import java.math.BigInteger
import java.security.MessageDigest

class PostsAdapter(val context: Context)
    : ListAdapter<Post, PostsAdapter.PostItemViewHolder>(PostDiffItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : PostItemViewHolder = PostItemViewHolder.inflateFrom(parent)
    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, context)
    }

    class PostItemViewHolder(val binding: PostItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): PostItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PostItemBinding.inflate(layoutInflater, parent, false)
                return PostItemViewHolder(binding)
            }
        }

        fun bind(post: Post, context: Context) {
            Glide.with(context).load(post.imageUrl).into(binding.ivPost)
        }
        private fun getProfileImageUrl(username: String): String {
            val digest = MessageDigest.getInstance("MD5")
            val hash = digest.digest(username.toByteArray())
            val bigInt = BigInteger(hash)
            val hex = bigInt.abs().toString(16)
            return "https://www.gravatar.com/avatar/$hex?d=identicon"
        }
    }
}