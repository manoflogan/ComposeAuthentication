package com.manoflogan.compose.home.composables

import android.content.res.Configuration
import android.provider.DocumentsContract.Root
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.manoflogan.compose.home.Destination

@Composable
fun NavigationRailBody(
    modifier: Modifier = Modifier, navController: NavHostController,
    destination: Destination, orientation: Int,
    onNavigate: (Destination) -> Unit,
    onCreate: () -> Unit
) {
    Row(modifier = modifier) {
        if (destination.isRootDestination && orientation == Configuration.ORIENTATION_LANDSCAPE) {
            RailNavigationBar(currentDestination = destination, onNavigate = onNavigate) {
                onCreate()
            }
        }
        Navigation(modifier = Modifier.fillMaxSize(), navController = navController)
    }
}