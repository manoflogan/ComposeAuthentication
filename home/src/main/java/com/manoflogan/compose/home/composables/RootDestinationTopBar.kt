package com.manoflogan.compose.home.composables

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.manoflogan.compose.home.Destination
import com.manoflogan.compose.home.R
import kotlinx.coroutines.launch

@Composable
fun RootDestinationTopBar(
    modifier: Modifier, currentDestination: Destination, onDrawerOpen: () -> Unit,
    showSnackbar: (message: String) -> Unit) {

    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        navigationIcon = {
            IconButton(onClick = onDrawerOpen) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.cd_open_menu)
                )
            }
        },
        actions = {
            if (currentDestination != Destination.Feed) {
                val snackbarMessage = stringResource(id = R.string.cd_not_available)
                IconButton(
                    onClick = {
                        showSnackbar(snackbarMessage)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = stringResource(id = R.string.cd_more_information)
                    )
                }
            }
        }
    )
}