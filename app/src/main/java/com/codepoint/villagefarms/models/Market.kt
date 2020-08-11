package com.codepoint.villagefarms.models

data class Market(
    val marketName: String?,
    val dateConfigured: String?,
    val marketPrice: Double?,
    var objectId: String?

) {

    // Non-empty constructor
    constructor() : this("", "",0.0, "")
}



