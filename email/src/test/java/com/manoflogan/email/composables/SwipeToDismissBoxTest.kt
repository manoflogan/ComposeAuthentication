package com.manoflogan.email.composables

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.core.app.ApplicationProvider
import com.manoflogan.email.R
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
class SwipeToDismissBoxTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun validateThatSwipeToDismissBoxIsInitialised() {
        composeRule.setContent {
            SwipeDismissBox(
                modifier = Modifier.fillMaxWidth(),
                targetValue = SwipeToDismissBoxValue.Settled
            )
        }
        composeRule.run {
            onNode(hasSwipeDismissStateProperty(SwipeToDismissBoxValue.Settled))
                .assertContentDescriptionEquals(context.getString(R.string.inbox_delete))
                .assertIsDisplayed()
        }
    }

    @Test
    fun validateThatSwipeToDismissBoxGoesFromStartToEnd() {
        composeRule.setContent {
            SwipeDismissBox(
                modifier = Modifier.fillMaxWidth(),
                targetValue = SwipeToDismissBoxValue.StartToEnd
            )
        }
        composeRule.run {
            onNode(hasSwipeDismissStateProperty(SwipeToDismissBoxValue.StartToEnd))
                .assertContentDescriptionEquals(context.getString(R.string.inbox_delete))
                .assertIsDisplayed()
        }
    }
}