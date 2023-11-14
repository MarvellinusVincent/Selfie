package com.example.selfieaday.model

import com.google.firebase.firestore.PropertyName

/**
 * Data class representing a post with an image and creation time.
 *
 * @property imageUrl The URL of the image in the post.
 * @property creationTimeMs The creation time of the post in milliseconds.
 */
data class Post(
    @get:PropertyName("image_url") @set:PropertyName("image_url")
    var imageUrl: String = "",

    @get:PropertyName("creation_time_ms") @set:PropertyName("creation_time_ms")
    var creationTimeMs: Long = 0
)
