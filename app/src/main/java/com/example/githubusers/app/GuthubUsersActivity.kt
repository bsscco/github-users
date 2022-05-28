package com.example.githubusers.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.navDeepLink
import com.example.githubusers.aosutil.navigation.LocalNavController
import com.example.githubusers.designsys.theme.GithubUsersTheme
import com.example.githubusers.home.HomeScreen
import com.example.githubusers.navigation.Uris
import com.example.githubusers.userdetail.UserDetailScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class GithubUsersActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubUsersApp()
        }
    }
}

@Composable
private fun GithubUsersApp() {
    GithubUsersTheme {
        @OptIn(ExperimentalAnimationApi::class)
        CompositionLocalProvider(LocalNavController provides rememberAnimatedNavController()) {

            // 참고 : https://google.github.io/accompanist/navigation-animation
            AnimatedNavHost(navController = LocalNavController.current, startDestination = "home") {

                composable(
                    route = "home",
                    exitTransition = { fadeOut() },
                    popEnterTransition = { fadeIn() },
                ) {
                    HomeScreen()
                }

                composable(
                    route = "user?user_name={userName}",
                    deepLinks = listOf(navDeepLink { uriPattern = "${Uris.GithubUsers.USER_DETAIL}?user_name={userName}" }),
                    enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left) },
                    popExitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right) },
                ) { entry ->
                    val userName = entry.arguments!!.getString("userName")!!
                    UserDetailScreen(userName)
                }
            }
        }
    }
}
