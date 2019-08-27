package com.codepoint.villagefarms

data class Farmer(
    val firstname: String,
    val lastname: String,
    val title: String,
    val sex: String,
    val maritalStatus: String,
    val district: String,
    val traditionalAuthority: String,
    val phone: String,
    val mmRegistered: String,
    val mmPayment: String,
    val yearOpened: String,
    val matureTrees: Int?,
    val immatureTrees: Int?,
    val hectarage: Int,
    val created: String

)