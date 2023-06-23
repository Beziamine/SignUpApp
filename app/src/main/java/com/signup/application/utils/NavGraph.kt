package com.signup.application.utils

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.signup.application.viewmodels.HomeViewModel
import com.signup.application.views.ConfirmationScreen
import com.signup.application.views.SignUpScreen

@ExperimentalCoilApi
@Composable
fun NavGraph(navController: NavHostController, homeViewModel: HomeViewModel,
             activity: Activity) {
    NavHost(
        navController = navController,
        startDestination = Screen.SignUp.route
    ) {
        composable(route = Screen.SignUp.route) {
            SignUpScreen(navController, homeViewModel,activity)
        }
        composable(
            route = Screen.Confirmation.route,
            arguments = listOf(navArgument(Constant.USER_ID) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(Constant.USER_ID)
                ?.let { ConfirmationScreen(it, navController,homeViewModel) }
        }
    }
}