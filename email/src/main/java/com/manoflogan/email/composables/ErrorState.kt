package com.manoflogan.email.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.manoflogan.email.R
import com.manoflogan.email.data.InboxEvent
import com.manoflogan.email.data.InboxStatus

@Composable
fun ErrorState(modifier: Modifier = Modifier, onClick: (InboxEvent) -> Unit) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.inbox_error), style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.email_padding)))
        Button(onClick = { onClick(InboxEvent.RefreshEvent) }) {
            Text(text = stringResource(id = R.string.inbox_retry), style = MaterialTheme.typography.bodySmall)
        }
    }
}