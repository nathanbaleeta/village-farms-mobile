package com.codepoint.villagefarms



data class Farmer(
    val firstname: String,
    val lastname: String,
    val phone: String,
    val opened: String,
    val matureTrees: Int?,
    val immatureTrees: Int?,
    val hectarage: Int?
)