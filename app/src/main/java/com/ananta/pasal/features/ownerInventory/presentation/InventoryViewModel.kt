// features/owner/presentation/inventory/InventoryViewModel.kt
package com.ananta.pasal.features.owner.presentation.inventory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ananta.pasal.features.authentication.domain.repository.AuthRepository
import com.ananta.pasal.features.ownerInventory.domain.InventoryRepository
import com.ananta.pasal.source.local.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import android.net.Uri
import com.ananta.pasal.features.ownerInventory.presentation.InventoryUiState
import com.ananta.pasal.features.ownerInventory.presentation.ProductFormState
import com.ananta.pasal.source.local.entity.SyncStatus
import com.ananta.pasal.utils.copyImageToInternalStorage
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val inventoryRepository: InventoryRepository,
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow<InventoryUiState>(InventoryUiState.Loading)
    val uiState: StateFlow<InventoryUiState> = _uiState

    // for add/edit dialog
    private val _formState = MutableStateFlow(ProductFormState())
    val formState: StateFlow<ProductFormState> = _formState

    private var currentOwnerId: String = ""

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            authRepository.currentUser
                .filterNotNull()
                .take(1)
                .collect { user ->
                    currentOwnerId = user.uid
                    inventoryRepository.getProducts(user.uid)
                        .catch { e ->
                            _uiState.value = InventoryUiState.Error(e.message ?: "Failed to load products")
                        }
                        .collect { products ->
                            _uiState.value = InventoryUiState.Success(products)
                        }
                }
        }
    }

    // form actions
    fun onFormNameChange(value: String)        { _formState.update { it.copy(name = value) } }
    fun onFormDescChange(value: String)        { _formState.update { it.copy(description = value) } }
    fun onFormPriceChange(value: String)       { _formState.update { it.copy(price = value) } }
    fun onFormStockChange(value: String)       { _formState.update { it.copy(stockQuantity = value) } }
    fun onFormCategoryChange(value: String)    { _formState.update { it.copy(category = value) } }

    fun openAddDialog() {
        _formState.value = ProductFormState() // reset form
    }

    fun onFormImageChange(uri: Uri?) {
        _formState.update { it.copy(imageUri = uri) }
    }

    fun openEditDialog(product: Product) {
        _formState.value = ProductFormState(
            id          = product.id,
            name        = product.name,
            description = product.description,
            price       = product.price.toString(),
            stockQuantity = product.stockQuantity.toString(),
            category    = product.category,
            imageUri      = product.localImagePath?.let { Uri.parse(it) },
            isEditing   = true
        )
    }

    fun saveProduct() {
        val form = _formState.value
        if (!form.isValid()) return

        viewModelScope.launch {
            val permanentImagePath = form.imageUri?.let { uri ->
                if (uri.toString().startsWith("content://")) {
                    copyImageToInternalStorage(context, uri)
                } else {
                    uri.toString()  // already a permanent path
                }
            }

            val product = Product(
                id = if (form.isEditing) form.id!! else UUID.randomUUID().toString(),
                ownerId = currentOwnerId,
                name = form.name,
                description = form.description,
                price = form.price.toDouble(),
                stockQuantity = form.stockQuantity.toInt(),
                category = form.category,
                imageUrl = null,
                localImagePath = permanentImagePath,
                isDeleted = false,
                syncStatus = SyncStatus.PENDING,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
            if (form.isEditing) {
                inventoryRepository.updateProduct(product)
            } else {
                inventoryRepository.addProduct(product)
            }
            _formState.value = ProductFormState() // reset
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            inventoryRepository.deleteProduct(productId)
        }
    }
}


object Categories {
    val list = listOf(
        "Grains & Pulses",
        "Dairy",
        "Snacks",
        "Beverages",
        "Spices",
        "Cleaning",
        "Personal Care",
        "Other"
    )
}