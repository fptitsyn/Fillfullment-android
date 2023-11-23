package com.example.fillfullment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
            LoginScreen(onClick = {
                    navController.navigateSingleTopTo("${HomeDestination.route}/$it")
                })
        }
        composable(
            route = HomeDestination.routeWithArgs,
            arguments = listOf(navArgument(HomeDestination.userIdArg) {
                type = NavType.IntType
            })
        ) {
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