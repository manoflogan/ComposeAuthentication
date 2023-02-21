package com.manoflogan.compose.authentication.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.manoflogan.compose.authentication.R
import com.manoflogan.compose.authentication.SettingsViewModel
import com.manoflogan.compose.authentication.models.SettingsState

@Composable
fun Settings() {
    val viewModel: SettingsViewModel = viewModel()
    val uiState: SettingsState = viewModel.uiState.collectAsState().value
    SettingsList(uiState = uiState, viewModel)
}

@Composable
fun SettingsList(uiState: SettingsState, viewModel: SettingsViewModel, modifier: Modifier = Modifier) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = Modifier, scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            TopAppBar(
                modifier = Modifier.semantics {
                    heading()
                },
                backgroundColor = MaterialTheme.colors.surface,
                contentPadding = PaddingValues(start = 12.dp)
            ) {
                Icon(
                    tint = MaterialTheme.colors.onSurface,
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.header_settings_back)
                )
                Spacer(modifier = modifier.width(16.dp))
                Text(
                    text = stringResource(id = R.string.header_settings),
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(paddingValues = it)
        ) {
            // Enable Notifications
            NotificationSettingsComposable(
                title = stringResource(id = R.string.body_enable_notifications),
                accessibilityString = stringResource(
                    if (uiState.notificationsEnabled) {
                        R.string.notifications_enabled
                    } else {
                        R.string.notifications_disabled
                    }),
                checked = uiState.notificationsEnabled,
                onCheckedChanged =  { viewModel.toggleNotificationSettings() },
                modifier.fillMaxWidth()
            )
            Divider(thickness = 2.dp)
            // Enable hint
            EnableHintsComposable(
                enableHintsTitle = stringResource(id = R.string.show_hints),
                accessibilityString = stringResource(if (uiState.enableHint) R.string.hints_enabled else R.string.hints_disabled),
                checked = uiState.enableHint ,
                onValueChecked = {viewModel.toggleEnableHint()},
                modifier = Modifier.fillMaxWidth()
            )
            Divider(thickness = 2.dp)
            ManageSubscriptions(
                title = stringResource(id = R.string.manage_subscriptions),
                accessibilityString = stringResource(id = R.string.manage_subscriptions_accessibility),
                onClick = {  },
                modifier = Modifier.fillMaxWidth()
            )
            Divider(thickness = 2.dp)
            SectionSpacer(modifier = Modifier.fillMaxWidth())
        }
    }
}

/**
 * "Enable" notifications composable.
 */
@Composable
fun NotificationSettingsComposable(title: String, accessibilityString: String, checked: Boolean, onCheckedChanged: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    SettingsItem(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .toggleable(
                    checked, role = Role.Switch,
                    onValueChange = onCheckedChanged
                )
                .semantics {
                    stateDescription = accessibilityString
                }
                .padding(16.dp)
        )  {
            Text(
                text = title, fontSize = 16.sp,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.weight(1f)
            )
            Switch(checked = checked, onCheckedChange = null)
        }
    }
}

/**
 * Enable hints composable
 */
@Composable
fun EnableHintsComposable(enableHintsTitle: String, accessibilityString: String, checked: Boolean,
                          onValueChecked: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    SettingsItem(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .toggleable(
                    checked, role = Role.Checkbox,
                    onValueChange = onValueChecked
                )
                .semantics {
                    stateDescription = accessibilityString
                }
                .padding(16.dp)
        ) {
           Text(text = enableHintsTitle, modifier = Modifier.weight(1f))
           Checkbox(checked = checked, onCheckedChange = null)
        }
    }
}

/**
 * Manage subscriptions screen
 */
@Composable
fun ManageSubscriptions(title: String, accessibilityString: String, onClick: () -> Unit, modifier: Modifier) {
    SettingsItem(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .clickable(onClickLabel = accessibilityString, role = Role.Button) {
                    onClick()
                }
                .padding(16.dp)
        ) {
            Text(text = title, modifier = modifier.weight(1f))
            Icon(
                tint = MaterialTheme.colors.onSurface,
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}

/**
 * Common composable for settings items.
 */
@Composable
fun SettingsItem(modifier: Modifier, content: @Composable ()-> Unit) {
    Surface(modifier = modifier.heightIn(56.dp) // Maintain minimum height does not enforce a fixed height
    ) {
        content()
    }
}

@Composable
fun SectionSpacer(modifier: Modifier) {
    Box(modifier = modifier
        .heightIn(48.dp)
        .background(MaterialTheme.colors.onSurface.copy(alpha = 0.12f)))
}