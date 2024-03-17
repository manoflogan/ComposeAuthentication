package com.manoflogan.email.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.manoflogan.email.R
import com.manoflogan.email.data.Email

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailContent(
    modifier: Modifier = Modifier,
    height: Dp,
    email: Email,
    onAccessibilityDelete: () -> Unit,
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
                    .defaultMinSize(minHeight = height)
                    .fillMaxWidth()
                    .semantics {
                        customActions = listOf(
                            CustomAccessibilityAction(label = deleteAsString) {
                                onAccessibilityDelete.invoke()
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