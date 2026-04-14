package mx.edu.growup.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mx.edu.growup.ui.navigation.HomeContainer
import mx.edu.growup.ui.screens.LoginScreen
import mx.edu.growup.ui.screens.RegisterScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "register")
    {
        composable("login"){
            LoginScreen(navController)
        }
        composable("register"){
            RegisterScreen(navController = navController)
        }
        composable(
            route = "homeContainer/{userId}",
            arguments = listOf(navArgument("userId") {
                type = NavType.IntType
            })
        ){ backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            HomeContainer(navController, userId)
        }
    }
}