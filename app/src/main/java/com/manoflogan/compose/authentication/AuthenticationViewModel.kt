package com.manoflogan.compose.authentication

import androidx.lifecycle.ViewModel
import com.manoflogan.compose.authentication.models.AuthenticationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthenticationViewModel: ViewModel() {

    val _authenticationStateFlow= MutableStateFlow(AuthenticationState())
    val authenticationStateFlow = _authenticationStateFlow.asStateFlow()
}