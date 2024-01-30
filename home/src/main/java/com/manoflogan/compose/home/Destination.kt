package com.manoflogan.compose.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
    val imageVector: ImageVector? = null,
    val isRootDestination: Boolean = true
) {
    /**
     * Home destination
     */
    object Home: Destination("Home")

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

    object Settings: Destination("Settings", isRootDestination = false)

    object Upgrade: Destination("Upgrade", isRootDestination = false)

    object Logout: Destination("Logout")

    object Creation: Destination("creation", isRootDestination = false)

    object Add: Destination("add", imageVector = Icons.Default.Add, isRootDestination = false)

    companion object {
        fun from(path: String): Destination =
            when(path) {
                Feed.path -> Feed
                Calendar.path -> Calendar
                Contacts.path -> Contacts
                Settings.path -> Settings
                Upgrade.path -> Upgrade
                Logout.path -> Logout
                Creation.path -> Creation
                Add.path -> Add
                else -> Home
            }
    }
}
