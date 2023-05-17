package com.manoflogan.compose.authentication

import android.content.Context
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import com.manoflogan.compose.authentication.composables.Authentication
import com.manoflogan.compose.authentication.composables.AuthenticationButton
import com.manoflogan.compose.authentication.composables.AuthenticationContent
import com.manoflogan.compose.authentication.composables.AuthenticationForm
import com.manoflogan.compose.authentication.composables.AuthenticationTitle
import com.manoflogan.compose.authentication.composables.Tags
import com.manoflogan.compose.authentication.composables.ToggleAuthenticationMode
import com.manoflogan.compose.authentication.models.AuthenticationMode
import com.manoflogan.compose.authentication.models.AuthenticationState
import com.manoflogan.compose.authentication.ui.theme.JetpackComposeAuthenticationTheme
import org.junit.Assert
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

    @Test
    fun testUser_Account_SignUp_Displayed_After_Toggling() {
        composeRule.setContent {
            Authentication()
        }
        composeRule.run {
            onNodeWithText(context.getString(R.string.need_account)).assertIsDisplayed().performClick()
            waitUntil {
                onAllNodesWithText(context.getString(R.string.have_account)).fetchSemanticsNodes()
                    .isNotEmpty()
            }
            onNodeWithText(context.getString(R.string.have_account)).assertIsDisplayed()
        }
    }

    @Test
    fun testUser_Account_SignIn_Displayed_After_Toggling() {
        composeRule.setContent {
            Authentication()
        }
        composeRule.run {
            onNodeWithTag(Tags.TAG_AUTHENTICATION_TOGGLE).apply {
                assertTextEquals(context.getString(R.string.need_account))
                performClick()
                waitUntil {
                    onAllNodesWithText(context.getString(R.string.have_account)).fetchSemanticsNodes()
                        .isNotEmpty()
                }
                assertTextEquals(context.getString(R.string.have_account))
            }
        }
    }

    @Test
    fun Authentication_Button_Disabled_By_Default() {
        composeRule.setContent {
            Authentication()
        }
        composeRule.run {
            onNodeWithTag(Tags.TAG_AUTHENTICATE_BUTTON).assertIsNotEnabled()
        }
    }

    @Test
    fun Authentication_Button_Enabled_With_Valid_Content() {
        composeRule.setContent {
            Authentication()
        }
        composeRule.run {
            onNodeWithTag(Tags.TAG_INPUT_EMAIL).performTextInput(EMAIL)
            onNodeWithTag(Tags.TAG_INPUT_PASSWORD).performTextInput(PASSWORD)
            waitUntil {
                onAllNodesWithText(EMAIL).fetchSemanticsNodes().size == 1 &&
                        onAllNodesWithTag(Tags.TAG_INPUT_PASSWORD).fetchSemanticsNodes().size == 1
            }
            onNodeWithTag(Tags.TAG_AUTHENTICATE_BUTTON).assertIsEnabled()
        }
    }

    @Test
    fun Authentication_Dialog_Not_Displayed() {
        composeRule.setContent {
            Authentication()
        }
        composeRule.run {
            onNodeWithTag(Tags.TAG_ERROR_ALERT).assertDoesNotExist()
        }
    }

    @Test
    fun Error_Dialog_Displayed_Error() {
        composeRule.setContent {
            AuthenticationContent(Modifier, authenticationState = AuthenticationState(error = "error")) {}
        }
        composeRule.run {
            onNodeWithTag(Tags.TAG_ERROR_ALERT).assertIsDisplayed()
        }
    }

    @Test
    fun Loading_Indicator_Is_Displayed() {
        composeRule.setContent {
            AuthenticationContent(modifier = Modifier,
                authenticationState = AuthenticationState(isLoading = true)
            ) {}
        }
        composeRule.run {
            onNodeWithTag(Tags.TAG_PROGRESS).assertIsDisplayed()
        }
    }

    @Test
    fun Loading_Indicator_Is_NotDisplayed() {
        composeRule.setContent {
            AuthenticationContent(modifier = Modifier,
                authenticationState = AuthenticationState()
            ) {}
        }
        composeRule.run {
            onNodeWithTag(Tags.TAG_PROGRESS).assertDoesNotExist()
        }
    }

    @Test
    fun testContent_Is_Displayed() {
        composeRule.setContent {
            Authentication()
        }
        composeRule.run {
            onNodeWithTag(Tags.TAG_INPUT_EMAIL).performTextInput(EMAIL)
            onNodeWithTag(Tags.TAG_INPUT_PASSWORD).performTextInput(PASSWORD)
            waitUntil {
                onAllNodesWithText(EMAIL).fetchSemanticsNodes().size == 1 &&
                        onAllNodesWithTag(Tags.TAG_INPUT_PASSWORD).fetchSemanticsNodes().size == 1
            }
            onNodeWithTag(Tags.TAG_CONTENT).assertExists()
            onNodeWithTag(Tags.TAG_AUTHENTICATE_BUTTON).performClick()
            waitUntil {
                onAllNodesWithTag(Tags.TAG_CONTENT).fetchSemanticsNodes().isEmpty()
            }
            onNodeWithTag(Tags.TAG_CONTENT).assertDoesNotExist()
        }
    }

    @Test
    fun testSignInIsDisplayed() {
        composeRule.setContent {
            AuthenticationTitle(modifier = Modifier, authenticationMode = AuthenticationMode.SIGN_IN)
        }
        composeRule.run {
            onNodeWithText(context.getString(R.string.label_sign_in_to_account)).assertIsDisplayed()
        }
    }

    @Test
    fun testSignOutIsDisplayed() {
        composeRule.setContent {
            AuthenticationTitle(modifier = Modifier, authenticationMode = AuthenticationMode.SIGN_UP)
        }
        composeRule.run {
            onNodeWithText(context.getString(R.string.label_sign_up_for_account)).assertIsDisplayed()
        }
    }

    @Test
    fun testAuthenticationButtonSignInDisplayed() {
        composeRule.setContent {
            AuthenticationButton(modifier = Modifier, authenticationMode = AuthenticationMode.SIGN_IN, false) {}
        }
        composeRule.run {
            onNodeWithTag(Tags.TAG_AUTHENTICATE_BUTTON).assertIsNotEnabled()
            onNodeWithText(context.getString(R.string.sign_in)).assertIsDisplayed()
        }
    }

    @Test
    fun testAuthenticationButtonSignInEnabledDisplayed() {
        var isInvoked = false
        composeRule.setContent {
            AuthenticationButton(modifier = Modifier, authenticationMode = AuthenticationMode.SIGN_IN, true) {
                isInvoked = true
            }
        }
        composeRule.run {
            onNodeWithTag(Tags.TAG_AUTHENTICATE_BUTTON).assertIsEnabled().assertTextEquals(context.getString(R.string.sign_in)).performClick()
            Assert.assertTrue(isInvoked)
        }
    }

    @Test
    fun testAuthenticationButtonSignUpEnabledDisplayed() {
        var isInvoked = false
        composeRule.setContent {
            AuthenticationButton(modifier = Modifier, authenticationMode = AuthenticationMode.SIGN_UP, true) {
                isInvoked = true
            }
        }
        composeRule.run {
            onNodeWithTag(Tags.TAG_AUTHENTICATE_BUTTON).assertIsEnabled().assertTextEquals(context.getString(R.string.sign_up)).performClick()
            Assert.assertTrue(isInvoked)
        }
    }

    @Test
    fun testAuthenticationButtonSignUpDisabledDisplayed() {
        var isInvoked = false
        composeRule.setContent {
            AuthenticationButton(modifier = Modifier, authenticationMode = AuthenticationMode.SIGN_UP, false) {
                isInvoked = true
            }
        }
        composeRule.run {
            onNodeWithTag(Tags.TAG_AUTHENTICATE_BUTTON).assertTextEquals(context.getString(R.string.sign_up)).assertIsNotEnabled()
            Assert.assertFalse(isInvoked)
        }
    }

    @Test
    fun testToggleOnSignUpShowsHaveAccount() {
        var isInvoked = false
        composeRule.setContent {
            ToggleAuthenticationMode(Modifier, AuthenticationMode.SIGN_UP) {
                isInvoked = true
            }
        }
        composeRule.run {
            onNodeWithTag(Tags.TAG_AUTHENTICATION_TOGGLE).assertTextEquals(context.getString(R.string.have_account)).performClick()
            Assert.assertTrue(isInvoked)
        }
    }

    @Test
    fun testToggleOnSignInShowsNeedAccount() {
        var isInvoked = false
        composeRule.setContent {
            ToggleAuthenticationMode(Modifier, AuthenticationMode.SIGN_IN) {
                isInvoked = true
            }
        }
        composeRule.run {
            onNodeWithTag(Tags.TAG_AUTHENTICATION_TOGGLE).assertTextEquals(context.getString(R.string.need_account)).performClick()
            Assert.assertTrue(isInvoked)
        }
    }

    companion object {
        private const val EMAIL = "test@test.com"
        private const val PASSWORD = "Passw0rd"
    }

}