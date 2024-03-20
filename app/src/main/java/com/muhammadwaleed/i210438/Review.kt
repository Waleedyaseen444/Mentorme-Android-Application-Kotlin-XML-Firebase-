package com.muhammadwaleed.i210438

data class Review(
    val reviewerName: String = "",
    val reviewText: String = "",
    val rating: Float = 0.0f
) {
    // No-argument constructor
    constructor() : this("", "", 0.0f)
}
