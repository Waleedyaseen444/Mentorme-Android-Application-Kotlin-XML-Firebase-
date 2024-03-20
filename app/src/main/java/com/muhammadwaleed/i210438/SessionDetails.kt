package com.muhammadwaleed.i210438

data class SessionDetails(
    val name: String,
    val profession: String,
    val date: String,
    val time: String,
    val imageUrl: String // Updated to store image URL
) {
    // No-argument constructor required for Firebase
    constructor() : this("", "", "", "", "")
}


