package com.example.githubusers.aosutil.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

/**
 * 참고 : https://stackoverflow.com/questions/70614765/jetpack-compose-how-to-centralize-navcontroller-and-inject-it
 */
val LocalNavController = staticCompositionLocalOf<NavHostController> { error("No LocalNavController provided") }