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
                    navController.navigate(OrdersDestination.route)
                })
        }
        composable(route = OrdersDestination.route) {
            HomeScreen(
                navigateToOrderEdit = {
                    navController.navigate("${EditOrderDestination.route}/$it")
                },
                onLogOut = {
                    navController.navigate(LoginDestination.finalRoute)
                }
            )
        }
        composable(
            route = EditOrderDestination.routeWithArgs,
            arguments = listOf(navArgument(EditOrderDestination.orderIdArg) {
                type = NavType.IntType
            })
        ) {
                EditOrderScreen(
                    navigateBack = { navController.popBackStack() },
                    onLogOut = { navController.navigate(LoginDestination.finalRoute) }
                )
            }
    }
}