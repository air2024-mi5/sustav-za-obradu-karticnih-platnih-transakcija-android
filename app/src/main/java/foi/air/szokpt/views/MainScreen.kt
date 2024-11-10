package foi.air.szokpt.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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

    Scaffold(
        bottomBar = {
            AnimatedNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "dashboard",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("dashboard") { DashboardView(navController) }
            composable("reports") { ReportsView(navController) }
            composable("daily_process") { DailyProcessScreen(navController) }
        }
    }
}