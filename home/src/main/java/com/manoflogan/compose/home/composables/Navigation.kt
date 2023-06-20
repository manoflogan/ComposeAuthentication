package com.manoflogan.compose.home.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.manoflogan.compose.home.Destination

/**
 * Navigation
 */
@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(modifier = modifier, navController = navController, startDestination = Destination.Home.path) {
        composable(Destination.Home.path) {
            ContentArea(modifier = Modifier.fillMaxSize(), destination = Destination.Home)

        }
        composable(Destination.Contacts.path) {
            ContentArea(modifier = Modifier.fillMaxSize(), destination = Destination.Contacts)
        }
        composable(Destination.Feed.path) {
            ContentArea(modifier = Modifier.fillMaxSize(), destination = Destination.Feed)

        }
        composable(Destination.Calendar.path) {
            ContentArea(modifier = Modifier.fillMaxSize(), destination = Destination.Calendar)
        }
        composable(Destination.Settings.path) {
            ContentArea(modifier = Modifier.fillMaxSize(), destination = Destination.Settings)
        }
        composable(Destination.Upgrade.path) {
            ContentArea(modifier = Modifier.fillMaxSize(), destination = Destination.Upgrade)
        }
        composable(Destination.Logout.path) {
            ContentArea(modifier = Modifier.fillMaxSize(), destination = Destination.Logout)
        }
        composable(Destination.Creation.path) {
            ContentArea(modifier = Modifier.fillMaxSize(), destination = Destination.Creation)
        }
        navigation(startDestination = Destination.Add.path, route = Destination.Creation.path) {
            composable(route = Destination.Add.path) {
                ContentArea(modifier = Modifier.fillMaxSize(), destination = Destination.Add)
            }
        }
    }
}