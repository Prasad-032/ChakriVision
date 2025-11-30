package com.example.chakrivison

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

sealed class Screen(val route: String) {
    object Landing : Screen("landing")
    object Home : Screen("home")
    object Search : Screen("search")
    object MyCourses : Screen("myCourses")
    object Profile : Screen("profile")
    object History : Screen("history")
    object About : Screen("about")
    object Contact : Screen("contact")
}

@Composable
fun AppNavigation(
    auth: FirebaseAuth,
    modifier: Modifier = androidx.compose.ui.Modifier
) {
    val navController = rememberNavController()
    val startDestination = if (auth.currentUser != null) Screen.Home.route else Screen.Landing.route

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Landing.route) {
            LandingScreen(
                auth = auth,
                onSignInSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Landing.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                auth = auth,
                navController = navController
            )
        }
        composable(Screen.Search.route) {
            SearchScreen(
                auth = auth,
                navController = navController
            )
        }
        composable(Screen.MyCourses.route) {
            MyCoursesScreen(
                auth = auth,
                navController = navController
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                auth = auth,
                navController = navController
            )
        }
        composable(Screen.History.route) {
            HistoryScreen(
                auth = auth,
                navController = navController
            )
        }
        composable(Screen.About.route) {
            AboutScreen(
                auth = auth,
                navController = navController
            )
        }
        composable(Screen.Contact.route) {
            ContactScreen(
                auth = auth,
                navController = navController
            )
        }
    }
}

