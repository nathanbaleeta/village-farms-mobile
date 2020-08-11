package com.codepoint.villagefarms.models

data class Market(
    val marketName: String?,
    val marketPrice: Double?,
    val created: String?
    //var objectId: String?

) {
    // Non-empty constructor
    constructor() : this("", 0.0,"")
}



