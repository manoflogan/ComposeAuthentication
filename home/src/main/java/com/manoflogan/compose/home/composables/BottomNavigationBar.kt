package com.manoflogan.compose.home.composables

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.manoflogan.compose.home.Destination

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    currentDestination: Destination,
    onNavigate: (Destination) -> Unit
) {
    BottomNavigation {
        NavigationBarItem.buildNavigationBarItems(currentDestination, onNavigate).forEach {
            BottomNavigationItem(
                selected = it.selected,
                onClick = it.onClick,
                icon = { it.icon() },
                label = {it.label()}
            )
        }
    }
}