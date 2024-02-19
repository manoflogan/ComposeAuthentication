package com.manoflogan.email.composables

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver

val ColorSemanticPropertyKey = SemanticsPropertyKey<Color>("colour")
var SemanticsPropertyReceiver.colourId by ColorSemanticPropertyKey