package com.manoflogan.email.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.manoflogan.email.R
import com.manoflogan.email.data.InboxEvent
import com.manoflogan.email.data.InboxStatus
import com.manoflogan.email.ui.theme.JetpackComposeAuthenticationTheme

@Composable
fun EmailInbox(
    modifier: Modifier = Modifier,
    inboxStatus: InboxStatus,
    onInboxEvent: (InboxEvent) -> Unit
) {
    Box(modifier = modifier) {
        when {
            inboxStatus is InboxStatus.Loading -> {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.inverseSurface,
                        strokeWidth = dimensionResource(id = R.dimen.stroke_width)
                    )
                }
            }
            inboxStatus is InboxStatus.HasEmails -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(inboxStatus.emails) { email ->
                        EmailContent(
                            modifier = Modifier
                                .padding(dimensionResource(id = R.dimen.email_padding))
                                .fillMaxSize(),
                            email = email,
                            onInboxEvent
                        )
                    }
                }
            }
        }
    }
}