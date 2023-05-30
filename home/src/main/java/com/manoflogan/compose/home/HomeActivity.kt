package com.manoflogan.compose.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.manoflogan.compose.home.composables.Home
import com.manoflogan.compose.home.ui.theme.JetpackComposeAuthenticationTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAuthenticationTheme {
                // A surface container using the 'background' color from the theme
                Home()
            }
        }
    }
}