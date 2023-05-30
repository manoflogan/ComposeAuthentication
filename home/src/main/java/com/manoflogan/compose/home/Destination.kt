package com.manoflogan.compose.home

import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(
    val path: String,
    val imageVector: ImageVector? = null
) {

    
}
