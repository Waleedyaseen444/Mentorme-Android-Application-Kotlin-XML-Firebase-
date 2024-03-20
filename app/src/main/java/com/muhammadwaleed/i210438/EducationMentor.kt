package com.muhammadwaleed.i210438


class EducationMentor {
    var name: String? = null
    var profession: String? = null
    var status: String? = null
    var pricePerSession: String? = null
    var isFavorite: Boolean = false

    constructor() {
        // Default constructor required for Firebase
    }

    constructor(
        name: String?,
        profession: String?,
        status: String?,
        pricePerSession: String?,
        isFavorite: Boolean
    ) {
        this.name = name
        this.profession = profession
        this.status = status
        this.pricePerSession = pricePerSession
        this.isFavorite = isFavorite
    }
}

