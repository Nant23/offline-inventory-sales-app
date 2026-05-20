package com.ananta.pasal.utils.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ananta.pasal.R

@Composable
fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.pasal_hub_logo),
        contentDescription = "Pasal Hub Logo",
        modifier = Modifier.size(300.dp)
    )
}