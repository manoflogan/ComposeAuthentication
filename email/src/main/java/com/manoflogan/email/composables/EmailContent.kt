package com.manoflogan.email.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.manoflogan.email.R
import com.manoflogan.email.data.Email
import com.manoflogan.email.data.InboxEvent

@Composable
fun EmailContent(modifier: Modifier = Modifier, email: Email, onInboxEvent: (InboxEvent) -> Unit) {
    Column(modifier = modifier) {
        Text(text = email.title, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.email_padding_half)))
        Text(text = email.description, style = MaterialTheme.typography.bodyMedium)
    }

}