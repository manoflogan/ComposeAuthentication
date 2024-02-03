package com.manoflogan.email

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.manoflogan.email.composables.Inbox
import com.manoflogan.email.ui.theme.JetpackComposeAuthenticationTheme

class InboxActivity : ComponentActivity() {

    private val inboxViewModel: InboxViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Inbox(
                modifier = Modifier.fillMaxSize(),
                viewModel = inboxViewModel
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackComposeAuthenticationTheme {
        Greeting("Android")
    }
}