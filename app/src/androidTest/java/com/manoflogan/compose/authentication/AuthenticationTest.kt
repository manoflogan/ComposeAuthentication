package com.manoflogan.compose.authentication

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import com.manoflogan.compose.authentication.composables.Authentication
import com.manoflogan.compose.authentication.ui.theme.JetpackComposeAuthenticationTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AuthenticationTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun test_User_SignInTitleByDefault() {
        composeRule.setContent {
            Authentication()
        }
        composeRule.run {
            onNodeWithText(context.getString(R.string.label_sign_in_to_account)).assertIsDisplayed()
        }
    }

    @Test
    fun test_User_Account_Displayed_By_Default() {
        composeRule.setContent {
            Authentication()
        }
        composeRule.run {
            onNodeWithText(context.getString(R.string.need_account)).assertIsDisplayed()
        }
    }
}