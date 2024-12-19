package com.manoflogan.email.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeRight
import com.manoflogan.email.data.Email
import com.manoflogan.email.ui.theme.JetpackComposeAuthenticationTheme
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [Config.OLDEST_SDK])
class EmailContentListTest {

    private lateinit var emails: List<Email>

    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setUp() {
        emails = listOf(
            Email(id = "1", title = "title", description = "description")
        )
    }

    @Test
    fun validateThatEmailListIsSeen() {
        composeRule.setContent {
            JetpackComposeAuthenticationTheme {
                EmailContentList(
                    modifier = Modifier.fillMaxSize(),
                    emails = emails
                ) {
                }
            }
        }
        composeRule.run {
            emails.forEach { email ->
                onNodeWithText(email.title).assertIsDisplayed()
                onNodeWithText(email.description).assertIsDisplayed()
            }
        }
    }

    @Test
    fun validateThatSwipeActionIsLessThanThresholdThenNoActionIsTaken() {
        composeRule.setContent {
            JetpackComposeAuthenticationTheme {
                EmailContentList(
                    modifier = Modifier.fillMaxSize(),
                    emails = emails
                ) {
                }
            }
        }
        composeRule.run {
            emails.first().also { email ->
                onNodeWithText(email.title).assertIsDisplayed()
                onNodeWithText(email.description).assertIsDisplayed()
                onNodeWithText(email.description).performTouchInput {
                    swipeRight()
                }
                onNodeWithText(email.title).assertIsNotDisplayed()
                onNodeWithText(email.description).assertIsNotDisplayed()
            }
        }
    }
}