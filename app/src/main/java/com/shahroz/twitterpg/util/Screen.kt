package com.shahroz.twitterpg.util

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Compose : Screen("compose_tweet")
}