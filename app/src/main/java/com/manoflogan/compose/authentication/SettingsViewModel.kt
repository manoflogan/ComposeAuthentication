package com.manoflogan.compose.authentication

import androidx.lifecycle.ViewModel
import com.manoflogan.compose.authentication.models.MarketingOption
import com.manoflogan.compose.authentication.models.SettingsState
import com.manoflogan.compose.authentication.models.Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel: ViewModel() {

    val _uiState = MutableStateFlow(SettingsState())
    val uiState = _uiState.asStateFlow()

    fun toggleNotificationSettings() {
        _uiState.value = _uiState.value.copy(notificationsEnabled = !_uiState.value.notificationsEnabled)
    }

    fun toggleEnableHint() {
        _uiState.value = _uiState.value.copy(enableHint = !_uiState.value.enableHint)
    }

    fun setMarketingOption(marketingOption: MarketingOption) {
        _uiState.value = _uiState.value.copy(marketingOption = marketingOption)
    }

    fun setTheme(theme: Theme) {
        _uiState.value = _uiState.value.copy(theme = theme)
    }
}