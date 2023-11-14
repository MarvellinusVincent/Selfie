package com.example.selfieaday

import android.content.Context
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.selfieaday.databinding.PostItemBinding
import com.example.selfieaday.model.Post
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Adapter for displaying a list of [Post] items in a RecyclerView.
 *
 * @param context The application context.
 */
class PostsAdapter(val context: Context)
    : ListAdapter<Post, PostsAdapter.PostItemViewHolder>(PostDiffItemCallback()) {

    /**
     * Called when RecyclerView needs a new [PostItemViewHolder] of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
        return PostItemViewHolder.inflateFrom(parent)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, context)
    }

    /**
     * View holder class for displaying a single [Post] item.
     */
    class PostItemViewHolder(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            /**
             * Inflates the layout for a [PostItemViewHolder].
             */
            fun inflateFrom(parent: ViewGroup): PostItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PostItemBinding.inflate(layoutInflater, parent, false)
                return PostItemViewHolder(binding)
            }
        }

        /**
         * Binds the data of a [Post] to the view holder.
         */
        fun bind(post: Post, context: Context) {
            Glide.with(context).load(post.imageUrl).into(binding.ivPost)

            /** Set click listener for the image */
            binding.ivPost.setOnClickListener {
                /** Call a function to handle the click and navigate to full-screen view */
                onImageClicked(post.imageUrl, context)
            }
        }

        /**
         * Handles the click on the image and navigates to the full-screen view.
         */
        private fun onImageClicked(imageUrl: String, context: Context) {
            /** You can navigate to a new fragment or activity here */
            /** For simplicity, let's assume you have a FullScreenFragment */
            val action = PostsFragmentDirections.actionPostsFragmentToFullScreenFragment(imageUrl)
            itemView.findNavController().navigate(action)
        }
    }
}
