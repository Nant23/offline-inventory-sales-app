package com.ananta.pasal.features.authentication.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ananta.pasal.features.authentication.domain.model.User
import com.ananta.pasal.features.authentication.domain.model.UserRole
import com.ananta.pasal.utils.components.AddressField
import com.ananta.pasal.utils.components.EmailTextField
import com.ananta.pasal.utils.components.NameField
import com.ananta.pasal.utils.components.PasswordTextField
import com.ananta.pasal.utils.components.PhoneNumberField
import com.ananta.pasal.utils.components.SSpacer
import com.ananta.pasal.utils.components.ShopNameField
import com.ananta.pasal.utils.components.TText

@Composable
fun RegisterScreen(
    //viewModel: RegisterViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: (User) -> Unit
) {
    val registerViewModel = androidx.hilt.navigation.compose.hiltViewModel<RegisterViewModel>()
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var role by remember { mutableStateOf(UserRole.CUSTOMER) }
    
    // Role specific fields
    var shopName by remember { mutableStateOf("") }
    var shopAddress by remember { mutableStateOf("") }
    var deliveryAddress by remember { mutableStateOf("") }

    val registerState by registerViewModel.registerState.collectAsState()

    LaunchedEffect(registerState) {
        if (registerState is AuthState.Success) {
            onRegisterSuccess((registerState as AuthState.Success).user)
            registerViewModel.resetState()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TText(24, "Create Account")
        //Text(text = "Create Account", fontSize = 24.sp, style = MaterialTheme.typography.headlineMedium)

        SSpacer(32)


        NameField(
            value = fullName,
            onValueChange = {fullName = it},
        )

        Spacer(modifier = Modifier.height(8.dp))

        EmailTextField(
            email = email,
            onValueChange = {email = it}
        )

        SSpacer(8)

        PasswordTextField(
            password = password,
            onValueChange = {password = it}
        )

        SSpacer(8)


        PhoneNumberField(
            value = phoneNumber,
            onValueChange = {phoneNumber = it}
        )

        SSpacer(16)
        Text("Select Role:")
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = role == UserRole.CUSTOMER,
                onClick = { role = UserRole.CUSTOMER }
            )
            Text("Customer")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = role == UserRole.OWNER,
                onClick = { role = UserRole.OWNER }
            )
            Text("Owner")
        }
        SSpacer(16)
        if (role == UserRole.OWNER) {

            ShopNameField(
                value = shopName,
                onValueChange = { shopName = it}
            )

            SSpacer(8)

            AddressField(
                value = shopAddress,
                onValueChange = { shopAddress = it},
                label = "Shop Address"
            )
        } else {
            AddressField(
                value = deliveryAddress,
                onValueChange = { deliveryAddress = it},
                label = "Delivery Address"
            )
        }

        SSpacer(16)

        if (registerState is AuthState.Error) {
            Text(
                text = (registerState as AuthState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {
                registerViewModel.register(
                    fullName, email, password, phoneNumber, role,
                    if (role == UserRole.OWNER) shopName else null,
                    if (role == UserRole.OWNER) shopAddress else null,
                    if (role == UserRole.CUSTOMER) deliveryAddress else null
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = registerState !is AuthState.Loading
        ) {
            if (registerState is AuthState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Register")
            }
        }

        TextButton(onClick = onNavigateToLogin) {
            Text("Already have an account? Login")
        }
    }
}
