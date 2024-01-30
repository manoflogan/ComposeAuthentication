package com.manoflogan.compose.home.composables

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.manoflogan.compose.home.Destination
import java.util.Locale

class NavigationBarItem(
    val selected: Boolean,
    val onClick: () -> Unit,
    val icon: @Composable () -> Unit,
    val label: @Composable () -> Unit
) {

    companion object {
        fun buildNavigationBarItems(
            currentDestination: Destination,
            onNavigate: (Destination) -> Unit
        ): List<NavigationBarItem> =
            arrayOf(
                Destination.Feed,
                Destination.Contacts,
                Destination.Calendar
            ).map {
                NavigationBarItem(
                    selected = currentDestination.path == it.path,
                    icon = {
                        it.imageVector?.let {
                            Icon(imageVector = it, contentDescription = null)
                        }
                    },
                    label = {
                        Text(text = it.path.replaceFirstChar {
                            it.titlecase(Locale.getDefault())
                        })
                    },
                    onClick = {
                        onNavigate(it)
                    }
                )
            }
    }
}