package com.manoflogan.compose.home.composables

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.manoflogan.compose.home.Destination
import com.manoflogan.compose.home.R

@Composable
fun RailNavigationBar(
    modifier: Modifier = Modifier,
    currentDestination: Destination,
    onNavigate: (Destination) -> Unit,
    onCreate: () -> Unit
) {
    NavigationRail(
        modifier = modifier,
        header = {
            FloatingActionButton(onClick = {
                onCreate()
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.create_item))
            }
        }
    ) {
        NavigationBarItem.buildNavigationBarItems(currentDestination, onNavigate).forEach {
            NavigationRailItem(
                selected = it.selected,
                icon = {
                    it.icon()
                },
                label = {
                    it.label()
                },
                onClick = it.onClick
            )
        }
    }
}