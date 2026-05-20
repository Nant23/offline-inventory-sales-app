package com.ananta.pasal.utils.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun EmailTextField(
    email: String,
    modifier : Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = email,
        label = {Text("Email")},
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(20.dp)
    )
}

@Composable
fun PasswordTextField(
    password: String,
    modifier : Modifier = Modifier,
    onValueChange: (String) -> Unit

) {
    OutlinedTextField(
        value = password,
        label = {Text("Password")},
        onValueChange = onValueChange,
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation(),
        shape = RoundedCornerShape(20.dp)

    )

}


@Composable
fun NameField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = value,
        label = {Text("Full Name")},
        onValueChange = onValueChange,
        singleLine = true,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun PhoneNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = value,
        label = {Text("Phone Number")},
        shape = RoundedCornerShape(20.dp),
        keyboardOptions = KeyboardOptions (
            keyboardType = KeyboardType.Phone
        ),
        onValueChange = { input ->
            if (input.all{ it.isDigit()}) {
                onValueChange(input)
            }

        },
        modifier = modifier.fillMaxWidth()

    )
}

@Composable
fun AddressField(
    value:String,
    modifier: Modifier = Modifier,
    label: String,
    onValueChange: (String) -> Unit
){
    OutlinedTextField(
        label = {Text(label)},
        value = value,
        shape = RoundedCornerShape(20.dp),
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth()

    )
}

@Composable
fun ShopNameField(
    value:String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
){
    OutlinedTextField(
        label = {Text("Shop Name")},
        value = value,
        shape = RoundedCornerShape(20.dp),
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth()

    )
}