package com.manoflogan.email.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsActions.CustomActions
import androidx.compose.ui.semantics.SemanticsNode
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsSelector
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeRight
import androidx.test.platform.app.InstrumentationRegistry
import com.manoflogan.email.R
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
        lateinit var dismissState: SwipeToDismissBoxState
        composeRule.setContent {
            dismissState =  rememberSwipeToDismissBoxState(
                initialValue = SwipeToDismissBoxValue.Settled
            )
            JetpackComposeAuthenticationTheme {
                EmailContent(
                    modifier = Modifier.fillMaxWidth(),
                    email = email,
                    onAccessibilityDelete = {
                    },
                    dismissState = dismissState
                )
            }
        }
        composeRule.run {
            onNodeWithTag(SWIPE_DISMISS_TAG).performTouchInput {
                // Swipe partially under the threshold to 40% of the horizontal distance
                // See https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:wear/compose/compose-foundation/src/androidTest/kotlin/androidx/wear/compose/foundation/BasicSwipeToDismissBoxTest.kt;l=136-146?q=SwipeToDismissBoxTest
                swipeRight()
            }
            mainClock.advanceTimeBy(100_000L)
            MatcherAssert.assertThat(
                dismissState.currentValue,
                Matchers.`is`(SwipeToDismissBoxValue.StartToEnd)
            )
        }
    }

    @Test
    fun testThatDeleteActionIsInvokedOnSwipeWithCustomActions() {
        var isDeleted = false
        composeRule.setContent {
            JetpackComposeAuthenticationTheme {
                EmailContent(
                    modifier = Modifier.fillMaxWidth(),
                    email = email,
                    onAccessibilityDelete = {
                        isDeleted = true
                    },
                    dismissState = rememberSwipeToDismissBoxState(
                        initialValue = SwipeToDismissBoxValue.StartToEnd,
                        positionalThreshold = {
                            1f
                        }
                    )
                )
            }
        }
        composeRule.run {
            val deleteLabel = InstrumentationRegistry.getInstrumentation().targetContext.getString( R.string.inbox_delete)
            onNodeWithTag(SWIPE_DISMISS_TAG).performCustomAccessibilityActionWithLabelMatching(deleteLabel) { true }
            onNodeWithText(email.description).assertIsNotDisplayed()
            MatcherAssert.assertThat(
                isDeleted, Matchers.`is`(true)
            )
        }
    }

    // See https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/ui/ui-test/src/androidInstrumentedTest/kotlin/androidx/compose/ui/test/actions/CustomAccessibilityActionsTest.kt?q=customActions%20Test
    // and https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/ui/ui-test/src/commonMain/kotlin/androidx/compose/ui/test/Actions.kt;l=665?q=performCustomAccessibilityActionWithLabelMatching&sq=s
    private fun SemanticsNodeInteraction.performCustomAccessibilityActionWithLabelMatching(
        predicateDescription: String? = null,
        labelPredicate: (label: String) -> Boolean
    ): SemanticsNodeInteraction {
        val node = fetchSemanticsNode()
        val actions = node.config.getOrNull(CustomActions)!!
        val matchingActions = actions.filter { labelPredicate(it.label) }
        if (matchingActions.isEmpty()) {
            throw AssertionError("No custom accessibility actions matched [$predicateDescription].")
        } else if (matchingActions.size > 1) {
            throw AssertionError(
                "Expected exactly one custom accessibility action to match" +
                            " [$predicateDescription], but found ${matchingActions.size}.",
                )
        }
        matchingActions[0].action()
        return this
    }

    private fun buildGeneralErrorMessage(
        errorMessage: String,
        selector: SemanticsSelector,
        node: SemanticsNode
    ): String =
        buildString {
            appendLine(errorMessage)

            appendLine("Semantics of the node:")
            appendLine(node.config)

            append("Selector used: (")
            append(selector.description)
            appendLine(")")
        }



}