package com.ananta.pasal.features.authentication.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ananta.pasal.features.authentication.domain.model.User
import com.ananta.pasal.ui.theme.PasalTheme
import com.ananta.pasal.utils.components.EmailTextField
import com.ananta.pasal.utils.components.Logo
import com.ananta.pasal.utils.components.PasswordTextField
import com.ananta.pasal.utils.components.SSpacer
import com.ananta.pasal.utils.components.TTextButton

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: (User) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        if (loginState is AuthState.Success) {
            onLoginSuccess((loginState as AuthState.Success).user)
            viewModel.resetState()  // ← clean up after navigation
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Logo()

        SSpacer(15)

        EmailTextField(
            email = email,
            onValueChange = { email = it }
        )

        SSpacer(8)

        PasswordTextField(
            password = password,
            onValueChange = {password = it}
        )

        SSpacer(16)

        if (loginState is AuthState.Error) {
            Text(
                text = (loginState as AuthState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = { viewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            enabled = loginState !is AuthState.Loading,

        ) {
            if (loginState is AuthState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text(
                    "Login",
                    fontSize = 18.sp
                )
            }
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Text("Don't have an account?")
            TTextButton(onClick = onNavigateToRegister, text = " Register")
        }
    }
}
