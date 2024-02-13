package com.manoflogan.email.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.manoflogan.email.R
import com.manoflogan.email.data.Email
import com.manoflogan.email.data.InboxEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailContent(
    modifier: Modifier = Modifier,
    height: Dp,
    email: Email,
    dismissState: SwipeToDismissBoxState
) {
    SwipeToDismissBox(
        modifier = modifier,
        state = dismissState,
        enableDismissFromStartToEnd = true,
        enableDismissFromEndToStart = false,
        backgroundContent = {
            SwipeDismissBox(
                modifier = Modifier.fillMaxWidth().height(height),
                currentValue = dismissState.currentValue,
                targetValue = dismissState.targetValue
            )
        }
    ) {
        Card(modifier = modifier.then(Modifier.fillMaxWidth()), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = email.title, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.email_padding_half)))
                Text(text = email.description, style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}