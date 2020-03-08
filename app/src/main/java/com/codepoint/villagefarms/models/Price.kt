package com.codepoint.villagefarms.models

data class Price(
    val district: String?,
    val dateConfigured: String?,
    val pricePerKg: Double?,
    var objectId: String?

) {

    // Non-empty constructor
    constructor() : this("", "",0.0, "")
}



