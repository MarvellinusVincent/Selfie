package com.example.selfieaday

import androidx.recyclerview.widget.DiffUtil
import com.example.selfieaday.model.Post

class PostDiffItemCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post)
            = (oldItem.imageUrl == newItem.imageUrl)
    override fun areContentsTheSame(oldItem: Post, newItem: Post) = (oldItem == newItem)
}