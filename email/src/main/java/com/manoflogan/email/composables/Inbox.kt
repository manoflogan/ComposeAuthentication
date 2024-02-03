package com.manoflogan.email.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.manoflogan.email.InboxViewModel
import com.manoflogan.email.R

@Composable
fun Inbox(modifier: Modifier = Modifier, viewModel: InboxViewModel = viewModel()) {
    MaterialTheme {
        InboxScaffold(
            modifier = modifier.then(Modifier.padding(dimensionResource(id = R.dimen.scaffold_padding))),
            inboxStatus = viewModel.inboxStatusStateFlow.collectAsState().value,
            onInboxEvent = { inboxEvent ->
                viewModel.handleContentEvent(inboxEvent)
            }
        )
    }
}