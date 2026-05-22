package com.ananta.pasal.utils.components

import android.widget.Button
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp


@Composable
fun TTextButton(
    onClick: () -> Unit,
    text: String,
) {
    TextButton(
        onClick = onClick,
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text,
        )
    }
}
