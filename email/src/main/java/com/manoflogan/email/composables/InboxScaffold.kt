package com.manoflogan.email.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.manoflogan.email.R
import com.manoflogan.email.data.InboxEvent
import com.manoflogan.email.data.InboxStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxScaffold(
    modifier: Modifier = Modifier, inboxStatus: InboxStatus, onInboxEvent: (InboxEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = when (inboxStatus) {
                            is InboxStatus.Loading,
                            is InboxStatus.Empty,
                            is InboxStatus.Error -> stringResource(id = R.string.inbox_loading)
                            is InboxStatus.HasEmails -> stringResource(id = R.string.inbox_title, inboxStatus.emails.size)
                        },
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.semantics {
                            heading()
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
            )
        }
    ) {
        EmailInbox(modifier = Modifier.padding(it).fillMaxWidth(), inboxStatus = inboxStatus , onInboxEvent = onInboxEvent)
    }
}