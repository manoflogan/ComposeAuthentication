package com.manoflogan.email.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.manoflogan.email.R
import com.manoflogan.email.data.Email
import com.manoflogan.email.data.InboxEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailContentList(
    modifier: Modifier = Modifier,
    emails: List<Email>,
    onInboxEvent: (InboxEvent) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(
            emails,
            key = {item -> item.id }
        ) { email ->
            var isEmailDeleted by remember {
                mutableStateOf(false)
            }
            val dismissBoxState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    if (it == SwipeToDismissBoxValue.StartToEnd) {
                        isEmailDeleted = true
                        onInboxEvent(InboxEvent.DeleteEvent(email.id))
                        true
                    } else {
                        isEmailDeleted = false
                        false
                    }
                },
                positionalThreshold = { distance ->
                    distance * .25f
                }
            )
            val emailHeight by animateDpAsState(targetValue =
                if (isEmailDeleted) {
                    0.dp
                } else {
                    120.dp
                },
                label = "email_height",
                animationSpec = tween(delayMillis = 300),
                finishedListener = {
                    if (it == 0.dp) {
                        isEmailDeleted = false
                    }
                }
            )

            EmailContent(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.email_padding_half))
                    .fillMaxWidth()
                    .height(emailHeight),
                email = email,
                height = emailHeight,
                dismissState = dismissBoxState
            )
            val dividerThickness by animateFloatAsState(targetValue =
                if (dismissBoxState.targetValue == SwipeToDismissBoxValue.Settled) {
                    2F
                } else {
                    0F
                },
                label = "divided_animation"
            )
            HorizontalDivider(modifier = Modifier.height(dimensionResource(id = R.dimen.email_padding_half)).alpha(dividerThickness))
        }
    }
}