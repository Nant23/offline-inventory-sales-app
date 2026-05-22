// utils/ImageUtils.kt
package com.ananta.pasal.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.util.UUID

fun copyImageToInternalStorage(context: Context, uri: Uri): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val fileName = "product_${UUID.randomUUID()}.jpg"
        val file = File(context.filesDir, fileName)
        file.outputStream().use { output ->
            inputStream.use { input ->
                input.copyTo(output)
            }
        }
        file.absolutePath  // return permanent path
    } catch (e: Exception) {
        null
    }
}