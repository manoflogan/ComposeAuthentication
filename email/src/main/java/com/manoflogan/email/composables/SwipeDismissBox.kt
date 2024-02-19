package com.manoflogan.email.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.manoflogan.email.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeDismissBox(
    modifier: Modifier = Modifier,
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
            .background(color = colour)
            .padding(dimensionResource(id = R.dimen.email_padding))
            .fillMaxWidth()
    ) {
        val iconColor by animateColorAsState(targetValue =
            when(targetValue) {
                SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.onError
                else -> MaterialTheme.colorScheme.onSurface
            },
            animationSpec = tween(),
            label = "icon_animate"
        )
        val iconSize by animateFloatAsState(targetValue =
            when(targetValue) {
                SwipeToDismissBoxValue.StartToEnd -> 2f
                else -> 1f
            },
            animationSpec = tween(),
            label = "icon_size"
        )

        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .scale(iconSize)
                .testTag(SwipeDismissBoxTags.TAG),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.inbox_delete),
            tint = iconColor
        )
    }
}

object SwipeDismissBoxTags {
    internal const val TAG = "SwipeDismissTag"
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SwipeToDismissBox_Preview() {
    val swipeToDismissValue = rememberSwipeToDismissBoxState()
    SwipeDismissBox(targetValue = swipeToDismissValue.targetValue)
}