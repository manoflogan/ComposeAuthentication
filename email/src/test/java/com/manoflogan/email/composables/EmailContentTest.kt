package com.manoflogan.email.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeRight
import androidx.compose.ui.unit.dp
import com.manoflogan.email.data.Email
import com.manoflogan.email.ui.theme.JetpackComposeAuthenticationTheme
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@OptIn(ExperimentalMaterial3Api::class)
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [Config.OLDEST_SDK])
class EmailContentTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var email: Email

    @Before
    fun setUp() {
        email =  Email(
            id = "1",
            title = "title",
            description = "description"
        )
    }

    @Test
    fun testThatEmailContentTestIsVisible() {
        composeRule.setContent {
            val swipeToDismissState = rememberSwipeToDismissBoxState()
            JetpackComposeAuthenticationTheme {
                EmailContent(
                    modifier = Modifier.fillMaxWidth(),
                    height = 120.dp,
                    email = email,
                    onAccessibilityDelete = { },
                    dismissState = swipeToDismissState
                )
            }
        }
        composeRule.run {
            onNodeWithText(email.description).assertIsDisplayed()
            onNodeWithText(email.title).assertIsDisplayed()
        }
    }

    @Test
    fun testThatDeleteActionIsInvokedOnSwipe() {
        var isDeleted = false
        var swipeToDismissState: SwipeToDismissBoxState? = null
        composeRule.setContent {
            swipeToDismissState = rememberSwipeToDismissBoxState()
            JetpackComposeAuthenticationTheme {
                EmailContent(
                    modifier = Modifier.fillMaxWidth(),
                    height = 120.dp,
                    email = email,
                    onAccessibilityDelete = {
                        isDeleted = true
                    },
                    dismissState = swipeToDismissState!!
                )
            }
        }
        composeRule.run {
            onNodeWithText(email.description).performTouchInput {
                swipeRight()
            }
            MatcherAssert.assertThat(
                swipeToDismissState!!.currentValue, CoreMatchers.equalTo(SwipeToDismissBoxValue.Settled)
            )
            onNodeWithText(email.description).assertIsNotDisplayed()
        }
    }
}