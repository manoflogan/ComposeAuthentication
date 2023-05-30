package com.manoflogan.compose.home.composables

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manoflogan.compose.home.Destination
import com.manoflogan.compose.home.R

/**
 * Home composable
 */
@Composable
fun Home(modifier: Modifier = Modifier) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar {
                Text(text = stringResource(id = R.string.app_name))
            }
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
        floatingActionButtonPosition = FabPosition.End
    ) {
        ContentArea(modifier = Modifier.padding(it), destination = Destination.Contacts)
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
                contentDescription = destination.title
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Text(text = destination.title, fontSize = 16.sp)
    }
}


@Composable
@Preview(uiMode = UI_MODE_NIGHT_NO)
fun ContentAreaPreview() {
    ContentArea(modifier = Modifier.padding(16.dp), destination = Destination.Contacts)
}