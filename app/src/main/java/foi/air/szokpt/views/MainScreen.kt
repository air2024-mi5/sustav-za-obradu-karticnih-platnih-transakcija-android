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
import com.google.gson.Gson
import foi.air.szokpt.ui.LoginPage
import foi.air.szokpt.ui.components.AnimatedNavigationBar
import foi.air.szokpt.views.app.AccountSearchView
import foi.air.szokpt.views.app.AccountView
import foi.air.szokpt.views.app.DailyProcessesDashboardView
import foi.air.szokpt.views.app.DashboardView
import foi.air.szokpt.views.app.RegistrationView
import foi.air.szokpt.views.app.TransactionDetailsView
import foi.air.szokpt.views.app.TransactionsView
import hr.foi.air.szokpt.ws.models.responses.User
import java.nio.charset.StandardCharsets
import java.util.UUID

const val ROUTE_DASHBOARD = "dashboard"
const val ROUTE_REPORTS = "reports"
const val ROUTE_DAILY_PROCESSING = "daily_processing"
const val ROUTE_ACCOUNT = "account"
const val ROUTE_REGISTRATION = "registration"
const val ROUTE_ALL_ACCOUNT_SEARCH = "all_account_search"
const val ROUTE_USER_ACCOUNT_OVERVIEW = "user_account"

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val isAuthenticated = remember { mutableStateOf(false) }

    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (view.context as android.app.Activity).window
        val windowInsetsController = WindowCompat.getInsetsController(window, view)
        windowInsetsController.isAppearanceLightStatusBars = false
    }

    Scaffold(
        bottomBar = {
            if (isAuthenticated.value)
                AnimatedNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginPage(
                    onSuccessfulLogin = {
                        isAuthenticated.value = true
                        navController.navigate("dashboard") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }
            composable("dashboard") { DashboardView(navController) }
            composable("reports") { TransactionsView(navController) }
            composable("daily_processing") { DailyProcessesDashboardView(navController) }
            composable("account") { AccountView(navController) }
            composable(
                route = "registration/{userType}",
                arguments = listOf(navArgument("userType") { type = NavType.StringType })
            ) { backStackEntry ->
                val userType = backStackEntry.arguments?.getString("userType") ?: "Unknown"
                RegistrationView(navController = navController, userType = userType)
            }
            composable("all_account_search") {
                AccountSearchView(
                    navController,
                )
            }
            composable(
                route = "user_account/{userJson}",
                arguments = listOf(navArgument("userJson") { type = NavType.StringType })
            ) { backStackEntry ->
                val encodedUserJson = backStackEntry.arguments?.getString("userJson")

                val userJson = encodedUserJson?.let {
                    java.net.URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
                }

                val gson = Gson()
                val user = gson.fromJson(userJson, User::class.java)
                AccountView(navController = navController, providedAccount = user)
            }

            composable(
                route = "transaction_details/{transactionGuid}",
                arguments = listOf(navArgument("transactionGuid") { type = NavType.StringType })
            ) { backStackEntry ->
                val transactionGuidString = backStackEntry.arguments?.getString("transactionGuid")
                val transactionGuid = transactionGuidString?.let { UUID.fromString(it) }
                    ?: return@composable
                TransactionDetailsView(
                    navController = navController,
                    transactionGuid = transactionGuid
                )
            }
        }
    }
}