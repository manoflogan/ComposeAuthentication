package com.manoflogan.email.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.SemanticsMatcher

@OptIn(ExperimentalMaterial3Api::class)
fun hasSwipeDismissStateProperty(swipeToDismissBoxValue: SwipeToDismissBoxValue): SemanticsMatcher =
    SemanticsMatcher.expectValue(SwipeDismissStateProperty, swipeToDismissBoxValue)