package com.example.selfieaday

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.selfieaday.databinding.FragmentFullScreenBinding

/**
 * Fragment for displaying a full-screen image.
 */
class FullScreenFragment : Fragment() {

    private var _binding: FragmentFullScreenBinding? = null
    private val binding get() = _binding!!

    /**
     * Inflates the layout for this fragment and loads the full-screen image using Glide.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFullScreenBinding.inflate(inflater, container, false)
        val view = binding.root

        /** Get the image URL from the arguments using Safe Args */
        val imageUrl = FullScreenFragmentArgs.fromBundle(requireArguments()).imageUrl

        /** Load the full-screen image using Glide */
        Glide.with(this).load(imageUrl).into(binding.ivFullScreenImage)
        return view
    }

    /**
     * Clears the view binding instance when the view is destroyed to avoid memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
