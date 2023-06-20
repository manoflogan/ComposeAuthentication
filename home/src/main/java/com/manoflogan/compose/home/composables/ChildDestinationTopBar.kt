package com.manoflogan.compose.home.composables

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.manoflogan.compose.home.Destination
import com.manoflogan.compose.home.R

@Composable
fun ChildDestinationTopBar(
    modifier: Modifier = Modifier, title: String, onNavigateUp: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.cd_navigate_up)
                )
            }
        }
    )
}


@Composable
@Preview
fun ChildDestinationTopBarPreview() {
    ChildDestinationTopBar(title = "Test title"){}
}