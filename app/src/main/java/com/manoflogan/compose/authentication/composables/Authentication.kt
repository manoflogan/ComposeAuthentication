package com.manoflogan.compose.authentication.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.manoflogan.compose.authentication.AuthenticationEvent
import com.manoflogan.compose.authentication.AuthenticationViewModel
import com.manoflogan.compose.authentication.R
import com.manoflogan.compose.authentication.models.AuthenticationMode
import com.manoflogan.compose.authentication.models.AuthenticationState

@Composable
fun Authentication() {
    val authenticationViewModel: AuthenticationViewModel = viewModel()
    val authenticationState = authenticationViewModel.authenticationStateFlow.collectAsState().value
    AuthenticationContent(Modifier.fillMaxWidth(), authenticationState,
        authenticationViewModel::handleAuthenticationEvent)
}

@Composable
fun AuthenticationContent(modifier: Modifier, authenticationState: AuthenticationState,
                          handleEvent: (AuthenticationEvent) -> Unit) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        AnimatedVisibility(visible = authenticationState.isLoading) {
            CircularProgressIndicator(modifier = Modifier)
        }
        Spacer(modifier = Modifier.height(32.dp))
        AuthenticationTitle(modifier = modifier.fillMaxWidth(), authenticationState.authenticationMode)
        Spacer(modifier = Modifier.height(48.dp))
        AuthenticationForm(modifier, authenticationState, onEmailChanged = {
            handleEvent(AuthenticationEvent.EmailChangedEvent(it))
        }) {
            handleEvent(AuthenticationEvent.PasswordChangedEvent(it))
        }
    }
}


@Composable
fun AuthenticationForm(modifier: Modifier = Modifier, authenticationState: AuthenticationState,
                       onEmailChanged: (String) -> Unit, onPasswordChanged: (String) -> Unit) {

    Card(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp), elevation = 4.dp
    ) {
        Column(modifier = modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            EmailInput(modifier = Modifier.fillMaxWidth(), authenticationState.email ?: "", onEmailChanged)
            PasswordInput(modifier = Modifier.fillMaxWidth(), password = authenticationState.password ?: "", onPasswordChanged)
        }
    }

}

@Composable
fun EmailInput(modifier: Modifier, email: String, onEmailChanged: (String) -> Unit) {
    TextField(
        modifier = modifier,
        value = email,
        onValueChange = {
            onEmailChanged(it)
        },
        label = {
            Text(text = stringResource(
                id = R.string.label_email)
            )
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Email, contentDescription = null)
        },
        singleLine = true,
    )
}

@Composable
fun PasswordInput(modifier: Modifier, password: String, onPasswordChanged: (String) -> Unit) {
    var isPasswordHidden by rememberSaveable { mutableStateOf(false) }
    TextField(
        modifier = modifier,
        value = password,
        onValueChange = {
            onPasswordChanged(it)
        },
        label = {
            Text(text = stringResource(
                id = R.string.label_password)
            )
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Lock, contentDescription = null)
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable(
                    onClickLabel =
                        stringResource(id =
                            if (isPasswordHidden) {
                                R.string.cd_show_password
                            } else {
                                R.string.cd_hide_password
                            }
                        )
                ) {
                  isPasswordHidden = !isPasswordHidden
                },
                imageVector = if (isPasswordHidden) {
                    Icons.Default.Visibility
                } else {
                    Icons.Default.VisibilityOff
                },
                contentDescription = null
            )
        },
        singleLine = true,
        visualTransformation = if (isPasswordHidden) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
    )
}

@Composable
fun AuthenticationTitle(modifier: Modifier, authenticationMode: AuthenticationMode) {
    Text(modifier = modifier,
        text = stringResource(
            id = if(authenticationMode == AuthenticationMode.SIGN_IN) {
                R.string.label_sign_in_to_account
            } else {
                R.string.label_sign_up_for_account
            }
        ),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}