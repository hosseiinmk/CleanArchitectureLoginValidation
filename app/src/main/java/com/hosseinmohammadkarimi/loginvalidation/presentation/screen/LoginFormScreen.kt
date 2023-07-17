package com.hosseinmohammadkarimi.loginvalidation.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hosseinmohammadkarimi.loginvalidation.presentation.LoginFormEvent
import com.hosseinmohammadkarimi.loginvalidation.presentation.utils.UIEvent

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class,
)
@Composable
fun LoginFormScreen(
    viewModel: LoginFormViewModel = viewModel()
) {

    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val state = viewModel.state

    LaunchedEffect(key1 = snackbarHostState) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.SnackbarShow -> {
                    keyboard?.hide()
                    focusManager.clearFocus()
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        withDismissAction = true
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Login To Account",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(32.dp))
            if (state.usernameError != null) {
                Text(text = state.usernameError)
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = state.username,
                onValueChange = { viewModel.onEvent(LoginFormEvent.OnUsernameChanged(it)) },
                placeholder = {
                    Text(text = "username")
                },
                isError = state.usernameError != null,
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (state.passwordError != null) {
                Text(text = state.passwordError)
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = state.password,
                onValueChange = { viewModel.onEvent(LoginFormEvent.OnPasswordChanged(it)) },
                placeholder = {
                    Text(text = "password")
                },
                isError = state.passwordError != null,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.onEvent(LoginFormEvent.OnSubmit)
            }) {
                Text(text = "Login")
            }
        }
    }
}