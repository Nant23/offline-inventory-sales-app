package com.ananta.pasal.utils.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
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

@Composable
fun TitleText(
    text: String
){
    Text(
        text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    )
}
