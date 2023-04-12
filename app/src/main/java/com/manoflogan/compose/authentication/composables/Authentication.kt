package com.manoflogan.compose.authentication.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import com.manoflogan.compose.authentication.models.PasswordRequirements

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
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (authenticationState.isLoading) {
            CircularProgressIndicator()
        } else {
            AuthenticationForm(
                modifier = Modifier.fillMaxSize(),
                authenticationState.email ?: "",
                {
                    handleEvent(AuthenticationEvent.EmailChangedEvent(it))
                },
                authenticationState.password ?: "",
                {
                    handleEvent(AuthenticationEvent.PasswordChangedEvent(it))
                },
                {
                    handleEvent(AuthenticationEvent.Authenticate)
                },
                authenticationState.passwordRequirements,
                authenticationState.authenticationMode,
                {
                    handleEvent(AuthenticationEvent.Authenticate)
                },

                authenticationState.isFormValid(),
                {
                    handleEvent(AuthenticationEvent.ToggleAuthenticationEvent)
                }
            )
            authenticationState.error?.let { error ->
                AuthenticationErrorDialog(
                    modifier,
                    error = error,
                    onDismiss = {
                        handleEvent(AuthenticationEvent.ErrorDismissed)
                    }
                )
            }
        }
    }
}


@Composable
fun AuthenticationForm(modifier: Modifier = Modifier, email: String, onEmailChanged: (String) -> Unit,
                       password: String, onPasswordChanged: (String) -> Unit, onDoneClicked: () -> Unit,
                       completedPasswordRequirements: List<PasswordRequirements>,
                       authenticationMode: AuthenticationMode, onSignIn: () -> Unit,
                       isAuthenticationEnabled: Boolean, onToggleAuthenticationMode: () -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(32.dp))
        AuthenticationTitle(Modifier, authenticationMode = authenticationMode)
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp), elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                val passwordRequester =  remember { FocusRequester() }
                EmailInput(modifier = Modifier.fillMaxWidth(), email, onEmailChanged) {
                    passwordRequester.requestFocus()
                }
                PasswordInput(modifier = Modifier.fillMaxWidth(), password = password, onPasswordChanged,
                    onDoneClicked
                )
                // Show only when the users are using the sign in route.
                if(authenticationMode == AuthenticationMode.SIGN_IN) {
                    PasswordRequirements(
                        modifier = Modifier.fillMaxWidth(),
                        passwordRequirements = completedPasswordRequirements
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                AuthenticationButton(authenticationMode = authenticationMode,
                    isEnabled = isAuthenticationEnabled, onClick = onSignIn)
            }
        }
        // To occupy the space so that the authentication toggle goes to the bottom.
        Spacer(modifier = Modifier.weight(1f))
        ToggleAuthenticationMode(modifier = Modifier.fillMaxWidth(), authenticationMode = authenticationMode, onToggleAuthenticationMode)
    }
}

@Composable
fun EmailInput(modifier: Modifier, email: String, onEmailChanged: (String) -> Unit, onNext: () -> Unit) {
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
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next, keyboardType = KeyboardType.Email
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                onNext()
            }
        )
    )
}

@Composable
fun PasswordInput(modifier: Modifier, password: String, onPasswordChanged: (String) -> Unit, onDone: () -> Unit) {
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
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone()
            }
        )
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

@Composable
fun Requirement(modifier: Modifier = Modifier, message: String, isSatisfied: Boolean) {
    val passwordStatus = stringResource(id =if (isSatisfied)  {
            R.string.password_requirements_satisfied
        } else {
            R.string.password_requirements_unsatisfied
        }
    )
    Row(
        modifier = modifier
            .padding(6.dp)
            .semantics(
                mergeDescendants = true
            ) {
                text = AnnotatedString(passwordStatus)
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        val tint = if (isSatisfied) {
            MaterialTheme.colors.onSurface
        } else {
            MaterialTheme.colors.onSurface.copy(alpha = 0.4f)
        }
        Icon(
            modifier = modifier.size(12.dp), imageVector = Icons.Default.Check,
            contentDescription =  null, tint = tint
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(modifier = Modifier.clearAndSetSemantics {  }, text = message, fontSize = 12.sp, color = tint)
    }
}

@Composable
fun PasswordRequirements(modifier: Modifier, passwordRequirements: List<PasswordRequirements>) {
    Column(modifier = modifier) {
        PasswordRequirements.values().forEach {
            Requirement(
                message = stringResource(id = it.stringResource),
                isSatisfied = passwordRequirements.contains(it)
            )
        }
    }
}

@Composable
fun AuthenticationButton(modifier: Modifier = Modifier, authenticationMode: AuthenticationMode, isEnabled: Boolean,
                         onClick: () -> Unit,) {
    Button(
        modifier = modifier.testTag(Tags.TAG_AUTHENTICATE_BUTTON), enabled = isEnabled,
        onClick = onClick
    ) {
        Text(
            text = stringResource(
                if (authenticationMode == AuthenticationMode.SIGN_IN) {
                    R.string.sign_in
                } else {
                    R.string.sign_in
                }
            ),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ToggleAuthenticationMode(modifier: Modifier, authenticationMode: AuthenticationMode, onToggleAuthenticationMode: () -> Unit) {
    Surface(modifier = modifier) {
        TextButton(modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .padding(8.dp),
            onClick = onToggleAuthenticationMode
        ) {
            Text(
                text = stringResource(
                    if (authenticationMode == AuthenticationMode.SIGN_IN) {
                        R.string.need_account
                    } else {
                        R.string.have_account
                    }
                )
            )
        }
    }
}

@Composable
fun AuthenticationErrorDialog(modifier: Modifier, error: String, onDismiss: () -> Unit) {
    AlertDialog(modifier = modifier,
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(stringResource(id = R.string.error_title), fontSize = 18.sp)
        },
        text = {
            Text(error)
        },
        confirmButton = {
            TextButton(onClick = { onDismiss()  }) {
                Text(text = stringResource(id = R.string.error_ok))
            }
        }
    )
}