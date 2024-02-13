package com.manoflogan.email.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
    var isEmailDeleted by remember {
        mutableStateOf(false)
    }
    LazyColumn(modifier = modifier) {
        items(
            emails,
            key = {item -> item.id }
        ) { email ->
            val dismissBoxState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    if (it == SwipeToDismissBoxValue.StartToEnd) {
                        isEmailDeleted = true
                        onInboxEvent(InboxEvent.DeleteEvent(email.id))
                    }
                    true
                },
                positionalThreshold = { 0.25F }
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
                    .padding(dimensionResource(id = R.dimen.email_padding))
                    .fillMaxWidth()
                    .height(emailHeight),
                email = email,
                height = emailHeight,
                dismissState = dismissBoxState
            )
        }
    }
}