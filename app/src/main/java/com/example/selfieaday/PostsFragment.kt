package com.example.selfieaday

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.selfieaday.databinding.FragmentPostsBinding

/**
 * Fragment for displaying a list of posts.
 */
class PostsFragment : Fragment() {
    val TAG = "PostsFragment"
    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    /**
     * Inflates the layout for this fragment and initializes the view model, sensors, and RecyclerView adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /** Inflate the layout for this fragment */
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        val view = binding.root

        /** Get the shared view model */
        val viewModel: PostsViewModel by activityViewModels()
        binding.viewModel = viewModel
        viewModel.initializeSensors(AccelerometerSensor(this.requireContext()))
        binding.lifecycleOwner = viewLifecycleOwner

        /** Set up RecyclerView adapter */
        val adapter = PostsAdapter(this.requireContext())
        binding.rvPosts.adapter = adapter

        /** Observe changes in the posts list and update the adapter */
        viewModel.posts.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        /** Observe the navigation event to the camera and navigate when needed */
        viewModel.navigateToCamera.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                this.findNavController().navigate(R.id.action_postsFragment_to_createFragment)
                viewModel.onNavigatedToCamera()
            }
        })

        return view
    }
}
