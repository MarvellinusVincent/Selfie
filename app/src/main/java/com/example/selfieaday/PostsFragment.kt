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


class PostsFragment : Fragment() {
    val TAG = "PostsFragment"
    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        val view = binding.root
        val viewModel : PostsViewModel by activityViewModels()
        binding.viewModel = viewModel
        viewModel.initializeSensors(AccelerometerSensor(this.requireContext()))
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = PostsAdapter(this.requireContext())
        binding.rvPosts.adapter = adapter
        viewModel.posts.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToCamera.observe(viewLifecycleOwner, Observer { navigate ->
            if(navigate) {
                this.findNavController().navigate(R.id.action_postsFragment_to_createFragment)
                viewModel.onNavigatedToCamera()
            }
        })
        return view
    }
}