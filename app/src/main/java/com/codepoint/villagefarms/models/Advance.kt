package com.codepoint.villagefarms.models

data class Advance(
    val advanceType: String?,
    var advanceAmount: Double?,
    val commodityAdvanced: String?,
    var commodityValue: Double?,
    val paymentMode: String?,
    val pricePerKg: Double?,
    val totalCoffeeWeight: Double?,
    val created: String?,
    var objectId: String?

) {

    // Non-empty constructor
    constructor() : this("", 0.0, "", 0.0,
        "", 0.0, 0.0, "",
        "")
}



