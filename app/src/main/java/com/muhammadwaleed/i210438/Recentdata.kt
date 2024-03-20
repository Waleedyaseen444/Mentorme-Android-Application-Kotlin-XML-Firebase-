package com.muhammadwaleed.i210438

data class Recentdata(
    val sampleText: String = "",
    val occupationText: String = "",
    val statusText: String = "",
    val priceText: String = "",
    val imageUrl: String? = null,
    val heartIconRes: Int = 0 // Change heartIconRes to Int resource ID
) {
    constructor() : this("", "", "", "", null, 0)
}
