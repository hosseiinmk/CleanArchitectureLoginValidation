package com.hosseinmohammadkarimi.loginvalidation.presentation

sealed class LoginFormEvent {
    data class OnUsernameChanged(val username: String): LoginFormEvent()
    data class OnPasswordChanged(val password: String): LoginFormEvent()
    object OnSubmit: LoginFormEvent()
}
