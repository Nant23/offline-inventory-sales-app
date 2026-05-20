package com.ananta.pasal.utils.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun TText(
    size: Int,
    text: String
){
    Text(
        text = text,
        fontSize = size.sp,
    )
}