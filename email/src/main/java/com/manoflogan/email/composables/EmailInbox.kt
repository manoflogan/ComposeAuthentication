package com.manoflogan.email.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.manoflogan.email.data.InboxEvent
import com.manoflogan.email.data.InboxStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailInbox(
    modifier: Modifier = Modifier,
    inboxStatus: InboxStatus,
    onInboxEvent: (InboxEvent) -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when {
            inboxStatus is InboxStatus.Loading -> {
                LoadingStatus(modifier = Modifier.fillMaxSize())
            }
            inboxStatus is InboxStatus.HasEmails -> {
                EmailContentList(
                    modifier = Modifier.fillMaxSize(), emails = inboxStatus.emails,
                    onInboxEvent = onInboxEvent
                )
            }
            inboxStatus is InboxStatus.Error -> {
                ErrorState(modifier = Modifier.fillMaxSize(), onClick = onInboxEvent)
            }
            inboxStatus is InboxStatus.Empty -> {
                EmptyState(modifier = Modifier.fillMaxSize(), onClick = onInboxEvent)
            }
        }
    }
}