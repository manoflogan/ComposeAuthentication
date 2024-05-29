package com.manoflogan.email.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.manoflogan.email.R
import com.manoflogan.email.data.Email
import com.manoflogan.email.data.InboxEvent

const val TAG_CONTENT = "content"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailContentList(
    modifier: Modifier = Modifier,
    emails: List<Email>,
    onInboxEvent: (InboxEvent) -> Unit
) {
    LazyColumn(modifier = modifier.testTag(TAG_CONTENT)) {
        items(emails, key = {item -> item.id }) { email ->
            val dismissBoxState = rememberSwipeToDismissBoxState(
                positionalThreshold = { distance ->
                    distance * .25f
                }
            )
            var isEmailDeleted by remember {
                mutableStateOf(false)
            }
            // See https://slack-chats.kotlinlang.org/t/16382816/hi-since-the-compose-bom-composebom-2024-02-00-version-the-i#865df5e5-39c6-4dd3-9bc2-8f087300dd8d
            LaunchedEffect(key1 = dismissBoxState) {
                snapshotFlow {
                    dismissBoxState.currentValue
                }.collect {
                    if (it == SwipeToDismissBoxValue.StartToEnd) {
                        isEmailDeleted = true
                        onInboxEvent(InboxEvent.DeleteEvent(email.id))
                    } else {
                        isEmailDeleted = false
                    }
                }
            }
            val emailHeight by animateDpAsState(targetValue =
                if (isEmailDeleted) {
                    0.dp
                } else {
                    120.dp
                },
                label = "email_height",
                animationSpec = tween(delayMillis = 300)
            )

            EmailContent(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.email_padding_half))
                    .defaultMinSize(minHeight = emailHeight)
                    .fillMaxWidth(),
                email = email,
                height = emailHeight,
                onAccessibilityDelete = onInboxEvent,
                dismissState = dismissBoxState
            )
        }
    }
}