package com.example.githubusers.aosutil.dimention

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

val Int.textDp: TextUnit
    @Composable get() = with(LocalDensity.current) { (this@textDp).dp.toSp() }

val Double.textDp: TextUnit
    @Composable get() = with(LocalDensity.current) { (this@textDp).dp.toSp() }