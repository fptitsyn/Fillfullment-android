package com.example.fillfullment.ui.navigation

interface NavDestination {
    val route: String
    val title: String
    val routeWithArgs: String
    val finalRoute: String
}

object LoginDestination : NavDestination {
    override val route = "login"
    override val title = "Login"
    override val routeWithArgs = ""
    override val finalRoute = routeWithArgs.ifBlank { route }
}

object OrdersDestination : NavDestination {
    override val route = "orders"
    override val title = "Orders"
    const val userIdArg = "userId"
    override val routeWithArgs = "$route/{$userIdArg}"
    override val finalRoute = routeWithArgs.ifBlank { route }
}

object EditOrderDestination : NavDestination {
    override val route = "edit-order"
    override val title = "Order"
    const val orderIdArg = "orderId"
    override val routeWithArgs = "$route/{$orderIdArg}"
    override val finalRoute = routeWithArgs.ifBlank { route }
}