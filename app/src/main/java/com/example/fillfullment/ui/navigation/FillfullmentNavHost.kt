package com.example.fillfullment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fillfullment.HomeScreen
import com.example.fillfullment.ui.login.LoginScreen

@Composable
fun FillfullmentNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = LoginDestination.finalRoute,
        modifier = modifier
    ) {
        composable(route = LoginDestination.finalRoute) {
            LoginScreen(
                onClick = { navController.navigateSingleTopTo(HomeDestination.finalRoute) }
            )
        }

        composable(route = HomeDestination.finalRoute) {
            HomeScreen()
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        this@navigateSingleTopTo.graph.startDestinationRoute?.let { route ->
            popUpTo(route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}