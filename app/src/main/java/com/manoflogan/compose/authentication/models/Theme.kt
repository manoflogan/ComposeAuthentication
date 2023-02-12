package com.manoflogan.compose.authentication.models

import androidx.annotation.StringRes
import com.manoflogan.compose.authentication.R

/**
 * Represents the theme
 */
enum class Theme(@StringRes val theme: Int) {
    LIGHT(R.string.theme_light),
    DARK(R.string.theme_dark),
    SYSTEM(R.string.theme_system)
}