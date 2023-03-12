package com.manoflogan.compose.authentication

import androidx.lifecycle.ViewModel
import com.manoflogan.compose.authentication.models.AuthenticationMode
import com.manoflogan.compose.authentication.models.AuthenticationState
import com.manoflogan.compose.authentication.models.PasswordRequirements
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthenticationViewModel: ViewModel() {

    val _authenticationStateFlow= MutableStateFlow(AuthenticationState())
    val authenticationStateFlow = _authenticationStateFlow.asStateFlow()

    fun toggleAuthenticationMode() {
        val authenticationState = _authenticationStateFlow.value
        val toggledAuthenticationMode =
            if (authenticationState.authenticationMode == AuthenticationMode.SIGN_IN) {
                AuthenticationMode.SIGN_UP
            } else {
                AuthenticationMode.SIGN_UP
            }
        _authenticationStateFlow.value =
            _authenticationStateFlow.value.copy(authenticationMode = toggledAuthenticationMode)
    }
    fun updateEmail(email: String) {
        _authenticationStateFlow.value = _authenticationStateFlow.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        val requirements = mutableListOf<PasswordRequirements>()
        if (password.length > 7) {
            requirements.add(PasswordRequirements.EIGHT_CHARACTERS)
        }
        if (password.any { it.isUpperCase() }) {
            requirements.add(PasswordRequirements.CAPITAL_LETTERS)
        }
        if (password.any { it.isDigit() }) {
            requirements.add(PasswordRequirements.NUMBER)
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
        }
    }
}