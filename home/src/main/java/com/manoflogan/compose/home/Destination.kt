package com.manoflogan.compose.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Sealed destination representing the destination for drawer layout, bottom navigation destination,
 * and floating action button navigation.
 */
sealed class Destination(
    val path: String,
    val imageVector: ImageVector? = null
) {

    /**
     * Home destination
     */
    object Home: Destination("home")

    /**
     * Feed
     */
    object Feed: Destination("Feed", Icons.Default.List)

    /**
     * Calendar
     */
    object Calendar: Destination("Calendar", Icons.Default.DateRange)

    /**
     * Contacts
     */
    object Contacts: Destination("contacts", Icons.Default.Person)
}
