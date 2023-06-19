package com.manoflogan.compose.home.composables

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DrawerValue
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.manoflogan.compose.home.Destination
import com.manoflogan.compose.home.R
import kotlinx.coroutines.launch

/**
 * Home composable
 */
@Composable
fun Home(modifier: Modifier = Modifier) {
    val navigationController = rememberNavController()
    val backStackEntry = navigationController.currentBackStackEntryAsState()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scaffoldState = rememberScaffoldState(drawerState)
    val currentDestination by remember(backStackEntry) {
        derivedStateOf {
            backStackEntry.value?.destination?.route?.let {
                Destination.from(it)
            } ?: run {
                Destination.Home
            }
        }
    }
    val navigationCallback: (Destination) -> Unit = {
        navigationController.navigate(it.path) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navigationController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
    ModalDrawer(
        drawerContent = {
            DrawerContent(navigationCallback) {

            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            modifier = modifier,
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    modifier = modifier,
                    title = {
                        Text(text = stringResource(id = R.string.app_name))
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(
                                    id = R.string.cd_open_menu
                                )
                            )
                        }
                    },
                    actions = {
                        if (currentDestination != Destination.Feed) {
                            val snackbarMessage = stringResource(id = R.string.cd_not_available)
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(snackbarMessage)
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = stringResource(id = R.string.cd_more_information)
                                )
                            }
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                    }
                ) {
                    Icon(
                        imageVector =Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.create_item)
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            bottomBar = {
                NavigationBottomBar(Modifier.fillMaxWidth(), currentDestination) {
                    navigationCallback.invoke(it)
                }
            },
            drawerContent = {
                DrawerContent(navigationCallback) {

                }
            }
        ) {
            Navigation(modifier = Modifier.padding(it), navigationController)
        }
    }
}

@Composable
fun ContentArea(modifier: Modifier = Modifier, destination: Destination) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        destination.imageVector?.let {
            Icon(
                modifier = Modifier.size(80.dp), imageVector = it,
                contentDescription = destination.path
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Text(text = destination.path, fontSize = 16.sp)
    }
}


@Composable
@Preview(uiMode = UI_MODE_NIGHT_NO)
fun ContentAreaPreview() {
    ContentArea(modifier = Modifier.padding(16.dp), destination = Destination.Contacts)
}