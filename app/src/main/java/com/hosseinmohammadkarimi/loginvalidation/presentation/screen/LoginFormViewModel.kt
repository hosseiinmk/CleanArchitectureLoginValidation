package com.hosseinmohammadkarimi.loginvalidation.presentation.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hosseinmohammadkarimi.loginvalidation.domain.use_case.ValidatePassword
import com.hosseinmohammadkarimi.loginvalidation.domain.use_case.ValidateUsername
import com.hosseinmohammadkarimi.loginvalidation.presentation.LoginFormEvent
import com.hosseinmohammadkarimi.loginvalidation.presentation.LoginFormState
import com.hosseinmohammadkarimi.loginvalidation.presentation.utils.UIEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginFormViewModel(
    private val validateUsername: ValidateUsername = ValidateUsername(),
    private val validatePassword: ValidatePassword = ValidatePassword()
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var state by mutableStateOf(LoginFormState())

    fun onEvent(event: LoginFormEvent) {
        when(event) {
            is LoginFormEvent.OnUsernameChanged -> {
                state = state.copy(username = event.username)
            }
            is LoginFormEvent.OnPasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is LoginFormEvent.OnSubmit -> {
                submit()
            }
        }
    }

    private fun submit() {
        val usernameResult = validateUsername.execute(state.username)
        val passwordResult = validatePassword.execute(state.password)
        val hasError = listOf(
            usernameResult,
            passwordResult
        ).any { !it.successful }
        if (hasError) {
            state = state.copy(
                usernameError = usernameResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            return
        }
        login()
    }

    private fun login() {
        viewModelScope.launch {
            _uiEvent.emit(
                UIEvent.SnackbarShow(
                    message = "با موفقیت وارد شدید"
                )
            )
        }
    }
}