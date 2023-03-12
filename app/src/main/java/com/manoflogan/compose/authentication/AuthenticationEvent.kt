package com.manoflogan.compose.authentication

sealed class AuthenticationEvent {

    /**
     * When user toggles the authentication mode from sign in to sign up and vice versa
     */
    object ToggleAuthenticationEvent: AuthenticationEvent()

    /**
     * When email has changed
     */
    class EmailChangedEvent(val email: String): AuthenticationEvent()

    /**
     * when the password has changed
     */
    class PasswordChangedEvent(val password: String): AuthenticationEvent()

    /**
     * Triggers authentication event
     */
    object Authenticate: AuthenticationEvent()

    object ErrorDismissed: AuthenticationEvent()
}
