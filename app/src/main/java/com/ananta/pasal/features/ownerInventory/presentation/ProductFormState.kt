package com.ananta.pasal.features.ownerInventory.presentation

import android.net.Uri
import com.ananta.pasal.features.owner.presentation.inventory.Categories


// form state for add/edit dialog
data class ProductFormState(
    val id: String?           = null,
    val name: String          = "",
    val description: String   = "",
    val price: String         = "",
    val stockQuantity: String = "",
    val category: String      = Categories.list.first(),
    val imageUri: Uri?         = null,
    val isEditing: Boolean    = false
) {
    fun isValid() = name.isNotBlank()
            && price.isNotBlank()
            && price.toDoubleOrNull() != null
            && stockQuantity.isNotBlank()
            && stockQuantity.toIntOrNull() != null
}