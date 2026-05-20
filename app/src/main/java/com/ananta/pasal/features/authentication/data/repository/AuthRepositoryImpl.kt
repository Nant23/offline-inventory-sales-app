package com.ananta.pasal.features.authentication.data.repository


import com.ananta.pasal.features.authentication.domain.model.User
import com.ananta.pasal.features.authentication.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    // observe auth state as a Flow
    override val currentUser: Flow<User?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser == null) {
                trySend(null)
            } else {
                // fetch full user doc from Firestore
                firestore.collection("users")
                    .document(firebaseUser.uid)
                    .get()
                    .addOnSuccessListener { doc ->
                        trySend(doc.toUser())
                    }
                    .addOnFailureListener {
                        trySend(null)
                    }
            }
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override suspend fun registerWithEmail(
        fullName: String,
        email: String,
        password: String,
        phoneNumber: String,
        role: String,
        shopName: String?,
        shopAddress: String?,
        deliveryAddress: String?
    ): Result<User> = runCatching {
        // 1. Create Firebase Auth account
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val uid = result.user!!.uid

        // 2. Build user map for Firestore
        val userMap = buildUserMap(
            uid, fullName, email, phoneNumber,
            role, shopName, shopAddress, deliveryAddress
        )

        // 3. Save to Firestore
        firestore.collection("users").document(uid).set(userMap).await()

        // 4. Return domain model
        User(
            uid = uid,
            fullName = fullName,
            email = email,
            phoneNumber = phoneNumber,
            role = role,
            shopName = shopName,
            shopAddress = shopAddress,
            deliveryAddress = deliveryAddress
        )
    }

    override suspend fun loginWithEmail(
        email: String,
        password: String
    ): Result<User> = runCatching {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        val uid = result.user!!.uid
        fetchUserFromFirestore(uid)
    }

//    override suspend fun signInWithGoogle(idToken: String): Result<User> = runCatching {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        val result = auth.signInWithCredential(credential).await()
//        val uid = result.user!!.uid
//
//        // check if user already exists in Firestore
//        val doc = firestore.collection("users").document(uid).get().await()
//        if (doc.exists()) {
//            doc.toUser()!!
//        } else {
//            // new Google user — needs role selection
//            // return a partial user, UI will ask for role
//            User(
//                uid = uid,
//                fullName = result.user?.displayName ?: "",
//                email = result.user?.email ?: "",
//                phoneNumber = "",
//                role = "",          // empty — UI must prompt for role
//                shopName = null,
//                shopAddress = null,
//                deliveryAddress = null
//            )
//        }
//    }

    override suspend fun logout() {
        auth.signOut()
    }

    override suspend fun getCurrentUserRole(): String? {
        val uid = auth.currentUser?.uid ?: return null
        val doc = firestore.collection("users").document(uid).get().await()
        return doc.getString("role")
    }

    private suspend fun fetchUserFromFirestore(uid: String): User {
        val doc = firestore.collection("users").document(uid).get().await()
        return doc.toUser() ?: throw Exception("User not found in Firestore")
    }

    private fun buildUserMap(
        uid: String,
        fullName: String,
        email: String,
        phoneNumber: String,
        role: String,
        shopName: String?,
        shopAddress: String?,
        deliveryAddress: String?
    ) = hashMapOf(
        "uid"             to uid,
        "fullName"        to fullName,
        "email"           to email,
        "phoneNumber"     to phoneNumber,
        "role"            to role,
        "shopName"        to shopName,
        "shopAddress"     to shopAddress,
        "deliveryAddress" to deliveryAddress,
        "createdAt"       to System.currentTimeMillis()
    )
}

fun DocumentSnapshot.toUser(): User? {
    if (!exists()) return null
    return User(
        uid             = getString("uid") ?: id,
        fullName        = getString("fullName") ?: "",
        email           = getString("email") ?: "",
        phoneNumber     = getString("phoneNumber") ?: "",
        role            = getString("role") ?: "",
        shopName        = getString("shopName"),
        shopAddress     = getString("shopAddress"),
        deliveryAddress = getString("deliveryAddress")
    )
}