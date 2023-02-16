package com.manoflogan.compose.authentication.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
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
            NotificationSettingsComposable(
                stringResource(id = R.string.body_enable_notifications),
                uiState.notificationsEnabled, {
                    viewModel.toggleNotificationSettings()
                },
                modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun NotificationSettingsComposable(title: String, checked: Boolean, onCheckedChanged: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    Surface(modifier = modifier.padding(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {

            Text(
                text = title, fontSize = 16.sp,
                color = MaterialTheme.colors.onSurface,
            )
            Switch(checked = checked, onCheckedChange = onCheckedChanged, modifier = Modifier.align(Alignment.Top))
        }
    }
}