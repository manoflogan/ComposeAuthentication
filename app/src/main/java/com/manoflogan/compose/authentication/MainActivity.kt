package com.manoflogan.compose.authentication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.manoflogan.compose.authentication.composables.Settings
import com.manoflogan.compose.authentication.ui.theme.JetpackComposeAuthenticationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAuthenticationTheme {
                Settings()
            }
        }
    }
}