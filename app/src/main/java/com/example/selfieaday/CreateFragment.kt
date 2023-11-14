package com.example.selfieaday

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.selfieaday.databinding.FragmentCreateBinding
import com.example.selfieaday.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date


class CreateFragment : Fragment() {
    val TAG = "CreateFragment"
    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!
    private var photoUri: Uri? = null
    private var uri: Uri? = null
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var storageReference: StorageReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        val view = binding.root
        val photoFile = createImageFile()
        try {
            uri = FileProvider.getUriForFile(this.requireContext(), "com.example.fileprovider", photoFile)
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.message}")
        }
        firestoreDb = FirebaseFirestore.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        val pickMedia = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                Log.d(TAG, "Selected URI: $uri")
                photoUri = uri
                postThePhoto()
            } else {
                Log.d(TAG, "No media selected")
            }
        }
        pickMedia.launch(uri)
        return view
    }


    private fun postThePhoto() {
        if (photoUri == null) {
            Toast.makeText(this.requireContext(), "No photo selected", Toast.LENGTH_SHORT).show()
            return
        }
        val photoUploadUri = photoUri as Uri
        val photoReference =
            storageReference.child("images/${System.currentTimeMillis()}-photo.jpg")
        // Upload photo to Firebase Storage
        photoReference.putFile(photoUploadUri)
            .continueWithTask { photoUploadTask ->
                Log.i(TAG, "uploaded bytes: ${photoUploadTask.result?.bytesTransferred}")
                // Retrieve image url of the uploaded image
                photoReference.downloadUrl
            }.continueWithTask { downloadUrlTask ->
                // Create a post object with the image URL and add that to the posts collection
                val post = Post(
                    downloadUrlTask.result.toString(),
                    System.currentTimeMillis(),
                )
                firestoreDb.collection("posts").add(post)
            }.addOnCompleteListener { postCreationTask ->
                if (!postCreationTask.isSuccessful) {
                    Log.e(TAG, "Exception during Firebase operations", postCreationTask.exception)
                    Toast.makeText(this.requireContext(), "Failed to save post", Toast.LENGTH_SHORT)
                        .show()
                }
                Toast.makeText(this.requireContext(), "Success!", Toast.LENGTH_SHORT).show()
            }
        navigateToPostsScreen()
    }

    private fun navigateToPostsScreen() {
        binding.root.findNavController().navigate(R.id.action_createFragment_to_postsFragment)
    }

    fun createImageFile() : File {
        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss")
            .format(Date())
        val imageDirectory = this.context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("file_${timestamp}"
            , ".jpg"
            , imageDirectory)
    }


}