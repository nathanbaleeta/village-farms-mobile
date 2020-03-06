package com.codepoint.villagefarms.models

data class District(
    val district: String?,
    val created: String?,
    var objectId: String?

) {

    // Non-empty constructor
    constructor() : this("", "","")
}



