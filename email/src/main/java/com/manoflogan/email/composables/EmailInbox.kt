package com.manoflogan.email.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.manoflogan.email.data.InboxEvent
import com.manoflogan.email.data.InboxStatus

@Composable
fun EmailInbox(
    modifier: Modifier = Modifier,
    inboxStatus: InboxStatus, onInboxEvent: (InboxEvent) -> Unit
) {

}