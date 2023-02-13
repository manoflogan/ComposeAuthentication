package com.manoflogan.compose.authentication.models

data class SettingsState(
    /**
     * If `true` then it means the notifications are enabled; `false` if not
     */
    var notificationsEnabled: Boolean = false,

    /**
     * If `true` then it means the hints are enabled; `false`
     */
    var enableHint: Boolean = true,

    /**
     * Represents the marketing option
     */
    var marketingOption: MarketingOption = MarketingOption.ALLOWED,

    /**
     * Represents the theme
     */
    var theme: Theme = Theme.SYSTEM
)