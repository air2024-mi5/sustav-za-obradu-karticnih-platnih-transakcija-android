package foi.air.szokpt.views

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import foi.air.szokpt.ExcelClearingFileGenerator
import foi.air.szokpt.pdf.PdfClearingFileGenerator
import foi.air.szokpt.ui.components.AnimatedNavigationBar
import foi.air.szokpt.views.app.AccountSearchView
import foi.air.szokpt.views.app.AccountView
import foi.air.szokpt.views.app.DailyProcessesDashboardView
import foi.air.szokpt.views.app.DashboardView
import foi.air.szokpt.views.app.EditTransactionView
import foi.air.szokpt.views.app.LoginView
import foi.air.szokpt.views.app.PreviousProcessingsView
import foi.air.szokpt.views.app.ProcessingDetailsView
import foi.air.szokpt.views.app.RegistrationView
import foi.air.szokpt.views.app.TransactionDetailsView
import foi.air.szokpt.views.app.TransactionsCandidatesView
import foi.air.szokpt.views.app.TransactionsView
import hr.foi.air.szokpt.core.file_generation.FileSavingOutcomeListener
import hr.foi.air.szokpt.core.file_generation.MediaStoreFileSaver
import hr.foi.air.szokpt.core.processing.ProcessingRecord
import hr.foi.air.szokpt.ws.models.responses.User
import java.nio.charset.StandardCharsets
import java.util.UUID

const val ROUTE_DASHBOARD = "dashboard"
const val ROUTE_REPORTS = "reports"
const val ROUTE_DAILY_PROCESSING = "daily_processing"
const val ROUTE_ACCOUNT = "account"
const val ROUTE_LOGIN = "login"
const val ROUTE_REGISTRATION = "registration"
const val ROUTE_ALL_ACCOUNT_SEARCH = "all_account_search"
const val ROUTE_USER_ACCOUNT_OVERVIEW = "user_account"
const val ROUTE_TRANSACTION_DETAILS = "transaction_details"
const val ROUTE_TRANSACTIONS_CANDIDATES = "processing_candidates"
const val ROUTE_EDIT_TRANSACTION = "edit_transaction"
const val ROUTE_PROCESSING_DETAILS = "latest_processing_details"
const val ROUTE_PREVIOUS_PROCESSINGS = "previous_processings"

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val isAuthenticated = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val mediaStoreFileSaver = MediaStoreFileSaver(context, object : FileSavingOutcomeListener {
        override fun onFailedSaving() {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(
                    context,
                    "Something went wrong while trying to save the document",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        override fun onFailedFileOpening() {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(
                    context,
                    "Could not open the generated file",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    })
    val clearingFileGenerators = mapOf(
        "Export PDF" to PdfClearingFileGenerator(mediaStoreFileSaver),
        "Export Excel" to ExcelClearingFileGenerator(mediaStoreFileSaver),
    )

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
            startDestination = ROUTE_LOGIN,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ROUTE_LOGIN) {
                LoginView(
                    onSuccessfulLogin = {
                        isAuthenticated.value = true
                        navController.navigate("dashboard") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }
            composable(ROUTE_DASHBOARD) { DashboardView() }
            composable(ROUTE_REPORTS) { TransactionsView(navController) }
            composable(ROUTE_DAILY_PROCESSING) { DailyProcessesDashboardView(navController) }
            composable(ROUTE_PREVIOUS_PROCESSINGS) { PreviousProcessingsView(navController) }
            composable(ROUTE_ACCOUNT) { AccountView(navController) }
            composable(
                route = ROUTE_REGISTRATION + "/{userType}",
                arguments = listOf(navArgument("userType") { type = NavType.StringType })
            ) { backStackEntry ->
                val userType = backStackEntry.arguments?.getString("userType") ?: "Unknown"
                RegistrationView(userType = userType)
            }
            composable(ROUTE_ALL_ACCOUNT_SEARCH) { AccountSearchView(navController) }
            composable(
                route = ROUTE_USER_ACCOUNT_OVERVIEW + "/{userJson}",
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
                route = ROUTE_TRANSACTION_DETAILS + "/{transactionGuid}",
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

            composable(ROUTE_TRANSACTIONS_CANDIDATES) { TransactionsCandidatesView(navController) }

            composable("$ROUTE_PROCESSING_DETAILS/{processingJson}?revertable={revertable}",
                arguments = listOf(
                    navArgument("processingJson") { type = NavType.StringType },
                    navArgument("revertable") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val encodedProcessingJson = backStackEntry.arguments?.getString("processingJson")
                val revertable =
                    backStackEntry.arguments?.getString("revertable")?.toBoolean() ?: false

                val processingJson = encodedProcessingJson?.let {
                    java.net.URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
                }

                val gson = Gson()
                val processing = gson.fromJson(processingJson, ProcessingRecord::class.java)

                ProcessingDetailsView(
                    clearingFileGenerators = clearingFileGenerators,
                    revertible = revertable,
                    processingRecord = processing
                )
            }

            composable(
                route = "${ROUTE_EDIT_TRANSACTION}/{transactionGuid}",
                arguments = listOf(navArgument("transactionGuid") { type = NavType.StringType })
            ) { backStackEntry ->
                val transactionGuidString = backStackEntry.arguments?.getString("transactionGuid")
                val transactionGuid =
                    transactionGuidString?.let { UUID.fromString(it) } ?: return@composable
                EditTransactionView(
                    navController = navController,
                    transactionGuid = transactionGuid
                )
            }
        }
    }
}