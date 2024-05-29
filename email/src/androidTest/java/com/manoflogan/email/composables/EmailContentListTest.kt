package com.manoflogan.email.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeRight
import androidx.compose.ui.test.swipeWithVelocity
import com.manoflogan.email.data.Email
import com.manoflogan.email.ui.theme.JetpackComposeAuthenticationTheme
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalMaterial3Api::class)
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

    /**@Test
    fun validateThatSwipeActionIsLessThanThresholdThenNoActionIsTaken() {
        composeRule.setContent {
            JetpackComposeAuthenticationTheme {
                EmailContentList(
                    modifier = Modifier.fillMaxSize(),
                    emails = emails,
                   /* dismissBoxState = rememberSwipeToDismissBoxState(
                        SwipeToDismissBoxValue.Settled,
                        confirmValueChange = {false}
                    )*/
                ) {
                }
            }
        }
        composeRule.run {
            emails.first().also { email ->
                onNodeWithText(email.title).assertIsDisplayed()
                onNodeWithText(email.description).assertIsDisplayed()
                onNodeWithText(email.description).performTouchInput {
                    // Swipe partially under the threshold to 20% of the horizontal distance
                    // See https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:wear/compose/compose-foundation/src/androidTest/kotlin/androidx/wear/compose/foundation/BasicSwipeToDismissBoxTest.kt;l=136-146?q=SwipeToDismissBoxTest
                    swipeWithVelocity(
                        start = Offset(0f, centerY),
                        end = Offset(centerX / 5f, centerY),
                        endVelocity = 1.0f
                    )
                }
                onNodeWithText(email.title).assertIsDisplayed()
                onNodeWithText(email.description).assertIsDisplayed()
            }
        }
    }

    @Test
    fun validateThatSwipeActionIsGreaterThanThresholdThenDeleteActionIsTaken() {
        var isDeleted = false
        composeRule.setContent {
            JetpackComposeAuthenticationTheme {
                EmailContentList(
                    modifier = Modifier.fillMaxSize(),
                    emails = emails,
                    /*dismissBoxState = rememberSwipeToDismissBoxState(
                        initialValue = SwipeToDismissBoxValue.StartToEnd,
                        confirmValueChange = {true},
                        positionalThreshold = {0F}
                    )*/
                ) {
                    isDeleted = true
                }
            }
        }
        composeRule.run {
            emails.forEachIndexed { index, email ->
                onNodeWithTag(TAG_CONTENT).onChildAt(index).performScrollTo().performTouchInput {
                    // Swipe partially under the threshold to 40% of the horizontal distance
                    // See https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:wear/compose/compose-foundation/src/androidTest/kotlin/androidx/wear/compose/foundation/BasicSwipeToDismissBoxTest.kt;l=136-146?q=SwipeToDismissBoxTest
                    swipeWithVelocity(
                        start = Offset(0f, centerY /2),
                        end = Offset(width.toFloat(), centerY),
                        endVelocity = 1.0f
                    )
                }
                MatcherAssert.assertThat(isDeleted, Matchers.equalTo(true))
            }
        }
    } */
}