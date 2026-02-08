package com.manoflogan.email.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.manoflogan.email.R
import com.manoflogan.email.data.Email
import com.manoflogan.email.data.InboxEvent
import kotlin.math.roundToInt

const val SWIPE_DISMISS_TAG = "swipeTag"

enum class DragAnchors(val fraction: Float) {
    Start(0f),
    Half(.5f),
    End(1f),
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmailContentDrag(
    email: Email, onAccessibilityDelete: (InboxEvent) -> Unit, modifier: Modifier = Modifier
) {
    val localDensity = LocalDensity.current
    // Calculate actual pixel values for the anchors
    val screenWidthPx = with(localDensity) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }

    val anchoredDraggableState = remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.Start,
            anchors = DraggableAnchors {
                DragAnchors.Start at DragAnchors.Start.fraction
                DragAnchors.Half at (screenWidthPx * DragAnchors.Half.fraction)
                DragAnchors.End at screenWidthPx * DragAnchors.End.fraction
            },
            positionalThreshold = { totalDistance: Float -> totalDistance * 0.5f },
            velocityThreshold = {
                with(localDensity) { 100.dp.toPx() }
            },
            snapAnimationSpec = tween(),
            decayAnimationSpec = exponentialDecay()
        )
    }
    val deleteAsString = stringResource(id = R.string.inbox_delete)

    // The Box acts as the container/background
    Box(modifier = modifier.fillMaxWidth()) {

        // The Card is the foreground that slides
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .offset {
                    IntOffset(
                        x = anchoredDraggableState
                            .requireOffset()
                            .roundToInt(),
                        y = 0
                    )
                }
                .anchoredDraggable(anchoredDraggableState,
                    Orientation.Horizontal)
                .semantics {
                    customActions = listOf(
                        CustomAccessibilityAction(label = deleteAsString) {
                            onAccessibilityDelete(InboxEvent.DeleteEvent(email.id))
                            true
                        }
                    )
                },
            elevation = CardDefaults.cardElevation()
        ) {
            ListItem(
                modifier = Modifier.fillMaxWidth(),
                headlineContent = {
                    Text(text = email.title, style = MaterialTheme.typography.headlineSmall)
                },
                supportingContent = {
                    Text(
                        text = email.description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailContent(
    modifier: Modifier = Modifier,
    email: Email,
    onAccessibilityDelete: (InboxEvent) -> Unit,
    dismissState: SwipeToDismissBoxState
) {
    SwipeToDismissBox(
        modifier = modifier,
        state = dismissState,
        enableDismissFromStartToEnd = true,
        enableDismissFromEndToStart = false,
        backgroundContent = {
            val deleteAsString = stringResource(id = R.string.inbox_delete)
            SwipeDismissBox(
                modifier = Modifier
                    .testTag(SWIPE_DISMISS_TAG)
                    .fillMaxSize()
                    .semantics {
                        customActions = listOf(
                            CustomAccessibilityAction(label = deleteAsString) {
                                onAccessibilityDelete(InboxEvent.DeleteEvent(email.id))
                                true
                            }
                        )
                    },
                targetValue = dismissState.targetValue
            )
        }
    ) {
        val cardElevation by animateDpAsState(targetValue =
            if (dismissState.targetValue == SwipeToDismissBoxValue.StartToEnd) {
                dimensionResource(id = R.dimen.email_padding_half)
            } else {
                   0.dp
            },
            label = "card_elevation"
        )
        Card(modifier = modifier.then(Modifier.fillMaxWidth()), elevation = CardDefaults.cardElevation(cardElevation)) {
            ListItem(
                modifier = Modifier.fillMaxWidth(),
                headlineContent = {
                    Text(text = email.title, style = MaterialTheme.typography.headlineSmall)
                },
                supportingContent = {
                    Text(text = email.description, style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2, overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    }
}