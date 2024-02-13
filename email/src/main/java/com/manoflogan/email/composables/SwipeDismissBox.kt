package com.manoflogan.email.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.manoflogan.email.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeDismissBox(
    modifier: Modifier = Modifier,
    currentValue: SwipeToDismissBoxValue,
    targetValue: SwipeToDismissBoxValue
) {
    val colour by animateColorAsState(
            when(targetValue) {
                SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.error
                else -> MaterialTheme.colorScheme.background
            },
        label = "dismiss_colour"
    )
    Box(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.email_padding))
            .fillMaxWidth()
            .background(color = colour)
    ) {
        Icon(
            modifier = Modifier.align(Alignment.CenterStart),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.inbox_delete))
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SwipeToDismissBox_Preview() {
    val swipeToDismissValue = rememberSwipeToDismissBoxState()
    SwipeDismissBox(currentValue = swipeToDismissValue.currentValue, targetValue = swipeToDismissValue.targetValue)
}