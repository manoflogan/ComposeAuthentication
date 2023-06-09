package com.manoflogan.compose.authentication.models

import androidx.annotation.StringRes
import com.manoflogan.compose.authentication.R

enum class PasswordRequirement(@StringRes val label: Int) {
    CAPITAL_LETTERS(R.string.password_requirement_capital),
    NUMBER(R.string.password_requirement_digit),
    EIGHT_CHARACTERS(R.string.password_requirement_characters)
}