package com.ananta.pasal.features.authentication.domain.model

data class User(
    val uid: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val role: String,           // "OWNER" or "CUSTOMER"
    val shopName: String?,      // Owner only
    val shopAddress: String?,   // Owner only
    val deliveryAddress: String?
)
