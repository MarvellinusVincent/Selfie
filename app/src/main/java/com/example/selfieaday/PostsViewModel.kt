package com.example.selfieaday

import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.selfieaday.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObjects

/**
 * ViewModel for managing data related to posts.
 */
class PostsViewModel : ViewModel() {

    val TAG = "PostsViewModel"
    private val _posts: MutableLiveData<MutableList<Post>> = MutableLiveData()
    private val _navigateToCamera = MutableLiveData<Boolean>(false)
    private lateinit var accelerometerSensor: MeasurableSensor
    private var accelerometerData = floatArrayOf(
        SensorManager.GRAVITY_EARTH, SensorManager.GRAVITY_EARTH, 0.0F
    )
    val posts: LiveData<List<Post>>
        get() = _posts as LiveData<List<Post>>
    val navigateToCamera: LiveData<Boolean>
        get() = _navigateToCamera

    /**
     * Initializes the ViewModel by retrieving posts from Firestore and setting up the snapshot listener.
     */
    init {
        val firestoreDB = FirebaseFirestore.getInstance()
        var postsReference = firestoreDB
            .collection("posts")
            .limit(30)
            .orderBy("creation_time_ms", Query.Direction.DESCENDING)

        postsReference.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                Log.e(TAG, "Exception when querying posts", exception)
                return@addSnapshotListener
            }
            val postList = snapshot.toObjects<Post>()
            _posts.value = postList as MutableList<Post>
            for (post in postList) {
                Log.i(TAG, "Post ${post}")
            }
        }
    }

    /**
     * Initializes the accelerometer sensor and sets up the listener for sensor data changes.
     */
    fun initializeSensors(sAccelerometer: MeasurableSensor) {
        accelerometerSensor = sAccelerometer
        accelerometerSensor.startListening()
        accelerometerSensor.setOnSensorValuesChangedListener { a ->
            val x: Float = a[0]
            val y: Float = a[1]
            val z: Float = a[2]
            accelerometerData[1] = accelerometerData[0]
            accelerometerData[0] = Math.sqrt((x * x).toDouble() + y * y + z * z).toFloat()
            val delta: Float = accelerometerData[0] - accelerometerData[1]
            accelerometerData[2] = accelerometerData[2] * 0.9f + delta
            if (accelerometerData[2] > 6) {
                Log.i(TAG, "Do not shake the phone!")
                _navigateToCamera.value = true
            }
        }
    }

    /**
     * Resets the navigation state after navigating to the camera.
     */
    fun onNavigatedToCamera() {
        _navigateToCamera.value = false
    }
}
