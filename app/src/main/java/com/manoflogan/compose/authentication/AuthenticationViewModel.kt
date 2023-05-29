package com.manoflogan.compose.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manoflogan.compose.authentication.models.AuthenticationMode
import com.manoflogan.compose.authentication.models.AuthenticationState
import com.manoflogan.compose.authentication.models.PasswordRequirement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthenticationViewModel: ViewModel() {

    val _authenticationStateFlow= MutableStateFlow(AuthenticationState())
    val authenticationStateFlow = _authenticationStateFlow.asStateFlow()

    fun toggleAuthenticationMode() {
        val authenticationState = _authenticationStateFlow.value
        val toggledAuthenticationMode =
            if (authenticationState.authenticationMode == AuthenticationMode.SIGN_IN) {
                AuthenticationMode.SIGN_UP
            } else {
                AuthenticationMode.SIGN_IN
            }
        _authenticationStateFlow.value =
            _authenticationStateFlow.value.copy(authenticationMode = toggledAuthenticationMode)
    }
    fun updateEmail(email: String) {
        _authenticationStateFlow.value = _authenticationStateFlow.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        val requirements = mutableListOf<PasswordRequirement>()
        if (password.length > 7) {
            requirements.add(PasswordRequirement.EIGHT_CHARACTERS)
        }
        if (password.any { it.isUpperCase() }) {
            requirements.add(PasswordRequirement.CAPITAL_LETTERS)
        }
        if (password.any { it.isDigit() }) {
            requirements.add(PasswordRequirement.NUMBER)
        }
        _authenticationStateFlow.value = _authenticationStateFlow.value.copy(
            password = password, passwordRequirements = requirements
        )
    }

    private fun authenticate() {
        _authenticationStateFlow.value = _authenticationStateFlow.value.copy(
            isLoading =  true
        )
        // Trigger netwok call.
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            _authenticationStateFlow.value = _authenticationStateFlow.value.copy(
                isLoading = false,
                error = "Something went wrong"
            )
        }
    }

    private fun dismissError() {
        _authenticationStateFlow.value = _authenticationStateFlow.value.copy(
            error = null
        )
    }

    fun handleAuthenticationEvent(authenticationEvent: AuthenticationEvent) {
        if (authenticationEvent is AuthenticationEvent.ToggleAuthenticationEvent) {
            toggleAuthenticationMode()
        } else if (authenticationEvent is AuthenticationEvent.EmailChangedEvent) {
            updateEmail(authenticationEvent.email)
        } else if (authenticationEvent is AuthenticationEvent.PasswordChangedEvent) {
            updatePassword(authenticationEvent.password)
        } else if (authenticationEvent is AuthenticationEvent.Authenticate) {
            authenticate()
        } else if (authenticationEvent is AuthenticationEvent.ErrorDismissed) {
            dismissError()
        }
    }
}