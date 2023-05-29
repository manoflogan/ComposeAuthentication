package com.manoflogan.compose.authentication.models

data class AuthenticationState(
    val authenticationMode: AuthenticationMode = AuthenticationMode.SIGN_IN,
    val email: String? = null,
    val password: String? = null,
    val passwordRequirements: List<PasswordRequirement> = emptyList(),
    // Progress state
    val isLoading: Boolean = false,
    // Error state
    val error: String? = null
) {

    /**
     * Return `true` if the the data is invalid, `false` if not
     */
    fun isFormValid(): Boolean =
        !email.isNullOrEmpty() && !password.isNullOrEmpty() &&
                (authenticationMode == AuthenticationMode.SIGN_IN &&
                        passwordRequirements.containsAll(PasswordRequirement.values().toList())
                )

}
