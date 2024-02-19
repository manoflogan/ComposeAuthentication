package com.manoflogan.email.composables

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.SemanticsMatcher

fun hasColour(colour: Color): SemanticsMatcher =
    SemanticsMatcher.expectValue(ColorSemanticPropertyKey, colour)