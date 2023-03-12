package com.manoflogan.compose.authentication

sealed class AuthenticationEvent {

    object ToggleAuthenticationEvent: AuthenticationEvent()

    class EmailChangedEvent(val email: String): AuthenticationEvent()

    class PasswordChangedEvent(val password: String): AuthenticationEvent()

    object Authenticate: AuthenticationEvent()
}
