package com.manoflogan.compose.home.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.manoflogan.compose.home.Destination

@Composable
fun DestinationTopBar(
    modifier: Modifier = Modifier, destination: Destination, onNavigateUp: () -> Unit,
    onOpenDrawer: () -> Unit, showSnackbar: (String) -> Unit
) {
    if (!destination.isRootDestination) {
        ChildDestinationTopBar(modifier = modifier, title = destination.path, onNavigateUp = onNavigateUp)
    } else {
        RootDestinationTopBar(
            modifier = modifier, currentDestination = destination,
            onDrawerOpen = onOpenDrawer,
            showSnackbar = showSnackbar
        )
    }
}