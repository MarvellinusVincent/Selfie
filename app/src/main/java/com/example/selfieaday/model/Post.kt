package com.example.selfieaday.model

import com.google.firebase.firestore.PropertyName


data class Post(
    @get:PropertyName("image_url") @set:PropertyName("image_url")
    var imageUrl: String = "",
    @get:PropertyName("creation_time_ms") @set:PropertyName("creation_time_ms")
    var creationTimeMs: Long = 0
)