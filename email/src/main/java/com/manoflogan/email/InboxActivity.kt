package com.manoflogan.email

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.manoflogan.email.composables.Inbox
import com.manoflogan.email.ui.theme.JetpackComposeAuthenticationTheme

class InboxActivity : ComponentActivity() {

    private val inboxViewModel: InboxViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAuthenticationTheme {
                Inbox(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = inboxViewModel
                )
            }
        }
    }
}