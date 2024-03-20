package com.muhammadwaleed.i210438

class Mentorhome {
    var name: String? = null
    var occupation: String? = null
    var status: String? = null
    var pricePerSession: String? = null
    var isFavorite: Boolean = false
    var imageUrl: String? = null

    constructor() {
        // Default constructor required for Firebase
    }

    constructor(
        name: String?,
        occupation: String?,
        status: String?,
        pricePerSession: String?,
        isFavorite: Boolean,
        imageUrl: String?
    ) {
        this.name = name
        this.occupation = occupation
        this.status = status
        this.pricePerSession = pricePerSession
        this.isFavorite = isFavorite
        this.imageUrl = imageUrl
    }
}
