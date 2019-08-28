package com.codepoint.villagefarms.models

data class Sale(
    val firstname: String,
    val lastname: String,
    val address: String,
    val goodPurchased: String,
    val phone: String,
    val unitPrice: Int?,
    val quantity: Int?,
    val totalPrice: Int,
    val created: String

)