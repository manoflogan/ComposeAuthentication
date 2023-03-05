package com.manoflogan.compose.authentication

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.manoflogan.compose.authentication.composables.AppVersion
import com.manoflogan.compose.authentication.composables.EnableHintsComposable
import com.manoflogan.compose.authentication.composables.ManageSubscriptions
import com.manoflogan.compose.authentication.composables.MarketingSettingItem
import com.manoflogan.compose.authentication.composables.NotificationSettingsComposable
import com.manoflogan.compose.authentication.composables.Tags
import com.manoflogan.compose.authentication.composables.ThemeSettingItem
import com.manoflogan.compose.authentication.models.MarketingOption
import com.manoflogan.compose.authentication.models.Theme
import com.manoflogan.compose.authentication.ui.theme.JetpackComposeAuthenticationTheme
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testAppVersion() {
        val version = "version"
        composeRule.setContent {
            JetpackComposeAuthenticationTheme {
                AppVersion(appVersion = version)
            }
        }
        composeRule.run {
            onNodeWithText(version).assertIsDisplayed()
            onNodeWithText(context.getString(R.string.app_version)).assertIsDisplayed()
        }
    }

    @Test
    fun testNotificationSettings() {
        var isSettingsChanged = false
        val title = "title"
        composeRule.setContent {
            JetpackComposeAuthenticationTheme {
                NotificationSettingsComposable(
                    title = title,
                    accessibilityString = title,
                    checked = true,
                    onCheckedChanged = {
                        isSettingsChanged = true
                    }
                )
            }
        }
        composeRule.run {
            onNodeWithTag(Tags.TOGGLE_ITEMS).assertIsDisplayed().performClick()
            waitForIdle()
            MatcherAssert.assertThat(isSettingsChanged, Matchers.`is`(true))
            onNodeWithTag(Tags.TOGGLE_ITEMS).assertIsOn()
        }
    }

    @Test
    fun testEnableHints() {
        var isHintsChanged = false
        val title = "title"
        composeRule.setContent {
            JetpackComposeAuthenticationTheme {
                EnableHintsComposable(
                    enableHintsTitle = title,
                    accessibilityString = title,
                    checked = true,
                    onValueChecked = {
                        isHintsChanged = true
                    }
                )
            }
        }
        composeRule.run {
            onNodeWithText(title).assertIsDisplayed().performClick()
            waitForIdle()
            MatcherAssert.assertThat(isHintsChanged, Matchers.`is`(true))
            onNodeWithText(title).assertIsOn()
        }
    }

    @Test
    fun testManageSubscriptions() {
        var manageSubscriptions = false
        val title = "title"
        composeRule.setContent {
            JetpackComposeAuthenticationTheme {
                ManageSubscriptions(
                    title = title,
                    accessibilityString = title,
                    onClick = {
                        manageSubscriptions = true
                    }
                )
            }
        }
        composeRule.run {
            onNodeWithText(title).assertIsDisplayed().performClick()
            waitForIdle()
            MatcherAssert.assertThat(manageSubscriptions, Matchers.`is`(true))
        }
    }

    @Test
    fun testManageSettings() {

        composeRule.setContent {
            JetpackComposeAuthenticationTheme {
                MarketingSettingItem(
                    title = "title",
                    selectedOption = MarketingOption.NOT_ALLOWED,
                    onOptionSelected = {
                       MatcherAssert.assertThat(
                           it, Matchers.`is`(MarketingOption.ALLOWED)
                       )
                    }
                )
            }
        }
        composeRule.run {
            onNodeWithTag("${Tags.TOGGLE_ITEMS}-0", useUnmergedTree = true).performClick()
            onNodeWithTag("${Tags.TOGGLE_ITEMS}-0", useUnmergedTree = true).assertIsSelected()
        }
    }

    @Test
    fun Selected_Theme_Displayed() {
        val option = Theme.DARK

        composeRule.setContent {
            ThemeSettingItem(selectedTheme = option, onThemeSelected = {})
        }

        composeRule.onNodeWithTag(Tags.TAG_THEME, useUnmergedTree = true)
            .assertTextEquals(
                InstrumentationRegistry.getInstrumentation().targetContext
                    .getString(option.label)
            )
    }

    @Test
    fun Theme_Options_Displayed() {
        composeRule.setContent {
            ThemeSettingItem(selectedTheme = Theme.DARK, onThemeSelected = {})
        }

        composeRule.onNodeWithTag(Tags.TAG_SELECT_THEME)
            .performClick()

        Theme.values().forEach { theme ->
            composeRule.onNodeWithTag(
                Tags.TAG_THEME_OPTION + InstrumentationRegistry.getInstrumentation().targetContext
                    .getString(theme.label)
            ).assertIsDisplayed()
        }
    }
}