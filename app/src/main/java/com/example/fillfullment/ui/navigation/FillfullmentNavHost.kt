package com.example.fillfullment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fillfullment.ui.order.HomeScreen
import com.example.fillfullment.ui.login.LoginScreen
import com.example.fillfullment.ui.order.EditOrderScreen

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
                navController.navigateSingleTopTo(OrdersDestination.route)
            })
        }
        composable(
            route = OrdersDestination.route,
//            arguments = listOf(navArgument(OrdersDestination.userIdArg) {
//                type = NavType.IntType
//            })
        ) {
            HomeScreen(navigateToOrderEdit = {
                navController.navigateSingleTopTo("${EditOrderDestination.route}/$it")
            })
        }
        composable(
            route = EditOrderDestination.routeWithArgs,
            arguments = listOf(navArgument(EditOrderDestination.orderIdArg) {
                type = NavType.IntType
            })
        ) {
                EditOrderScreen(
                    navigateBack = { navController.navigateSingleTopTo(OrdersDestination.route) }
                )
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