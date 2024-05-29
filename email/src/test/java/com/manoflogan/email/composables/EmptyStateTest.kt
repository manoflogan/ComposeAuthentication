package com.manoflogan.email.composables

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import com.manoflogan.email.R
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
class EmptyStateTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `verify that empty state is loaded`() {
        composeRule.setContent {
            EmptyState(modifier = Modifier.fillMaxSize(), onClick = {})
        }
        composeRule.run {
            onNodeWithText(context.getString(R.string.inbox_empty)).assertIsDisplayed()
            onNodeWithText(context.getString(R.string.inbox_check)).assertIsDisplayed()
        }
    }
}