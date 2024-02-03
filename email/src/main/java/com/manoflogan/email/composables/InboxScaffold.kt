package com.manoflogan.email.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.viewmodel.compose.viewModel
import com.manoflogan.email.InboxViewModel
import com.manoflogan.email.R
import com.manoflogan.email.data.InboxEvent
import com.manoflogan.email.data.InboxStatus
import com.manoflogan.email.ui.theme.JetpackComposeAuthenticationTheme

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
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.semantics {
                            heading()
                        }
                    )
                }
            )
        }
    ) {
        EmailInbox(modifier = Modifier.padding(it), inboxStatus = inboxStatus , onInboxEvent = onInboxEvent)
    }
}