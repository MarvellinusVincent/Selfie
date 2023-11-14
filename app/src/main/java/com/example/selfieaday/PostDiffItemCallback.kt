package com.example.selfieaday

import androidx.recyclerview.widget.DiffUtil
import com.example.selfieaday.model.Post

/**
 * Callback for calculating the difference between two lists of [Post] items.
 */
class PostDiffItemCallback : DiffUtil.ItemCallback<Post>() {

    /**
     * Called to check whether two items represent the same object or item.
     *
     * @param oldItem The old item in the list.
     * @param newItem The new item in the list.
     * @return True if the items represent the same object or item, false otherwise.
     */
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.imageUrl == newItem.imageUrl
    }

    /**
     * Called to check whether two items have the same data.
     *
     * @param oldItem The old item in the list.
     * @param newItem The new item in the list.
     * @return True if the items have the same data, false otherwise.
     */
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}
