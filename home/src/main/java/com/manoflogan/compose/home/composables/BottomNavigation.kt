package com.manoflogan.compose.home.composables

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.manoflogan.compose.home.Destination

@Composable
fun NavigationBottomBar(
    modifier: Modifier = Modifier, currentDestination: Destination,
    onNavigationClick: (Destination) -> Unit
) {
    BottomNavigation(modifier) {
        arrayOf(Destination.Home, Destination.Calendar, Destination.Contacts, Destination.Feed).forEach { destination ->
            BottomNavigationItem(
                selected = destination.path == currentDestination.path,
                onClick = {
                    onNavigationClick(destination)
                },
                icon = {
                    destination.imageVector?.let {
                        Icon(imageVector = it, contentDescription = destination.path)
                    }
                },
                label = {
                    Text(text = destination.path,  fontSize = 14.sp)
                }
            )
        }
    }
}