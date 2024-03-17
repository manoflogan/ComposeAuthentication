@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.manoflogan.email.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver

@OptIn(ExperimentalMaterial3Api::class)
val SwipeDismissStateProperty = SemanticsPropertyKey<SwipeToDismissBoxValue>("swipeToDismissProperty")
var SemanticsPropertyReceiver.swipeDismissStateId by SwipeDismissStateProperty