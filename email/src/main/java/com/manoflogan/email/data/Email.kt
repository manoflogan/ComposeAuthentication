package com.manoflogan.email.data

import androidx.compose.runtime.Immutable

@Immutable
data class Email(
    val id: String,
    val title: String,
    val description: String
)
