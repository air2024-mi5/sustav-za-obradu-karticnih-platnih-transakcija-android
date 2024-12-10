package foi.air.szokpt.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import foi.air.szokpt.helpers.LoginHandler
import foi.air.szokpt.ui.LoginPage
import foi.air.szokpt.ui.components.AnimatedNavigationBar
import foi.air.szokpt.views.app.AccountView
import foi.air.szokpt.views.app.DashboardView
import foi.air.szokpt.views.app.RegistrationView
import foi.air.szokpt.views.app.ReportsView
import foi.air.szokpt.views.test_views.DailyProcessScreen

const val ROUTE_DASHBOARD = "dashboard"
const val ROUTE_REPORTS = "reports"
const val ROUTE_DAILY_PROCESS = "daily_process"
const val ROUTE_ACCOUNT = "account"
const val ROUTE_REGISTRATION = "registration"


@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val isAuthenticated = remember { mutableStateOf(false) }

    // For the top status bar to be white on a back background of the app
    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (view.context as android.app.Activity).window
        val windowInsetsController = WindowCompat.getInsetsController(window, view)
        windowInsetsController.isAppearanceLightStatusBars = false
    }
    Scaffold(
        bottomBar = {
          // if(isAuthenticated.value)
              AnimatedNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "dashboard",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") { LoginPage(
                onSuccessfulLogin = {
                    isAuthenticated.value = true
                    navController.navigate("dashboard"){
                        popUpTo("login") { inclusive = true }
                    }
                }
            ) }
            composable("dashboard") { DashboardView(navController) }
            composable("reports") { ReportsView(navController) }
            composable("daily_process") { DailyProcessScreen(navController) }
            composable("account") { AccountView(navController) }
            composable(
                route = "registration/{userType}",
                arguments = listOf(navArgument("userType") { type = NavType.StringType })
            ) { backStackEntry ->
                val userType = backStackEntry.arguments?.getString("userType") ?: "Unknown"
                RegistrationView(navController = navController, userType = userType)
            }
        }
    }
}