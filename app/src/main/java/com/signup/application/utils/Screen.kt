package com.signup.application.utils

sealed class Screen(val route: String) {
    object SignUp : Screen("signup")
    object Confirmation : Screen("confirmation/{userId}") {
        fun passUserId(userId: String) = "confirmation/$userId"
    }
}