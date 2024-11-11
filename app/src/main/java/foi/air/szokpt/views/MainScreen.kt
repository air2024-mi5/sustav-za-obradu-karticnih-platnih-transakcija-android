package foi.air.szokpt.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import foi.air.szokpt.helpers.LoginHandler
import foi.air.szokpt.ui.LoginPage
import foi.air.szokpt.ui.components.AnimatedNavigationBar
import foi.air.szokpt.views.app.DashboardView
import foi.air.szokpt.views.app.ReportsView
import foi.air.szokpt.views.test_views.DailyProcessScreen

const val ROUTE_DASHBOARD = "dashboard"
const val ROUTE_REPORTS = "reports"
const val ROUTE_DAILY_PROCESS = "daily_process"

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val isAuthenticated = remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            if(isAuthenticated.value)
                AnimatedNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
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
        }
    }
}