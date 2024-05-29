package com.manoflogan.email.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [Config.OLDEST_SDK])
class LoadingStatusTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun validateThatLoadingStatusIsShown() {
        composeRule.setContent {
            LoadingStatus(
                modifier = Modifier.fillMaxSize()
            )
        }
        composeRule.run {
            onNodeWithTag(LoadingStateTags.TAG).assertIsDisplayed()
        }
    }
}