package com.manoflogan.email.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.manoflogan.email.R
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
                EmailContentList(modifier = Modifier.fillMaxSize(), emails = inboxStatus.emails,
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