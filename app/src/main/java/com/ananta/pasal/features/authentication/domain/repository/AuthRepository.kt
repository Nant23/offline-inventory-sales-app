package com.ananta.pasal.features.authentication.domain.repository

import com.ananta.pasal.features.authentication.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>

    suspend fun registerWithEmail(
        fullName: String,
        email: String,
        password: String,
        phoneNumber: String,
        role: String,
        shopName: String?,
        shopAddress: String?,
        deliveryAddress: String?
    ): Result<User>

    suspend fun loginWithEmail(email: String, password: String): Result<User>

    //suspend fun signInWithGoogle(idToken: String): Result<User>

    suspend fun logout()

    suspend fun getCurrentUserRole(): String?
}