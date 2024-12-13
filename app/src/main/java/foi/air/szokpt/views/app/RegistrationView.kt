package foi.air.szokpt.views.app

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import foi.air.szokpt.helpers.RegistrationHandler
import foi.air.szokpt.ui.components.LoginTextField
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.components.dialog_components.DialogComponent
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.BGLevelTwo
import foi.air.szokpt.ui.theme.BGLevelZeroLow
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.Secondary
import foi.air.szokpt.ui.theme.TextGray
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode
import foi.air.szokpt.ui.theme.success
import foi.air.szokpt.viewmodels.RegistrationViewModel
import hr.foi.air.core.register.RegistrationBody
import hr.foi.air.core.register.Role

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationView(navController: NavController, userType: String) {

    val viewModel: RegistrationViewModel = viewModel()

    val username = viewModel.username.observeAsState().value ?: ""
    val password = viewModel.password.observeAsState().value ?: ""
    val name = viewModel.firstName.observeAsState().value ?: ""
    val lastName = viewModel.lastName.observeAsState().value ?: ""
    val email = viewModel.email.observeAsState().value ?: ""
    val role = viewModel.role.observeAsState().value ?: ""
    val message by viewModel.message.observeAsState("")
    var showMessage by remember { mutableStateOf(false) }

    val registrationHandler = RegistrationHandler()
    var isAwaitingResponse by remember { mutableStateOf(false) }

    val openDialog = remember { mutableStateOf(false) }

    LaunchedEffect(message) {
        if (message.isNotEmpty()) {
            showMessage = true
        }
    }

    fun validateInput(): String {
        return if (username.isBlank() || password.isBlank() || name.isBlank() || lastName.isBlank() || email.isBlank()) {
            "All fields must be filled!"
        } else if (password.length < 3) {
            "Password must contain at least 3 characters."
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            "The email must be in the correct format."
        } else ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = "Registration",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                TileSegment(
                    tileSizeMode = TileSizeMode.WRAP_CONTENT,
                    innerPadding = 12.dp,
                    outerMargin = 4.dp,
                    minWidth = 250.dp,
                    minHeight = 20.dp,
                    color = BGLevelOne
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val options = listOf("user", "admin")
                        var selectedIndex by remember {
                            mutableStateOf(
                                options.indexOf(userType).coerceAtLeast(0)
                            )
                        }
                        SingleChoiceSegmentedButtonRow {
                            options.forEachIndexed { index, label ->
                                val isSelected = index == selectedIndex
                                SegmentedButton(
                                    shape = SegmentedButtonDefaults.itemShape(
                                        index = index,
                                        count = options.size
                                    ),
                                    onClick = { selectedIndex = index },
                                    selected = isSelected,
                                    modifier = Modifier,
                                    colors = SegmentedButtonDefaults.colors(
                                        activeContainerColor = Secondary,
                                        activeContentColor = TextWhite,
                                        activeBorderColor = Primary,
                                        inactiveContainerColor = BGLevelZeroLow,
                                        inactiveContentColor = TextGray,
                                        inactiveBorderColor = TextGray,
                                    )
                                ) {
                                    Text(
                                        label,
                                        color = if (isSelected) Color.White else Color.Gray,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }

                        val spacerHeight = 12.dp
                        Spacer(modifier = Modifier.height(spacerHeight))
                        LoginTextField(
                            label = "Username",
                            value = username,
                            onValueChange = { viewModel.username.value = it },
                            isPasswordField = false,
                        )
                        Spacer(modifier = Modifier.height(spacerHeight))
                        LoginTextField(
                            label = "Password",
                            value = password,
                            onValueChange = { viewModel.password.value = it },
                            isPasswordField = true,
                        )
                        Spacer(modifier = Modifier.height(spacerHeight))
                        LoginTextField(
                            label = "Name",
                            value = name,
                            onValueChange = { viewModel.firstName.value = it },
                            isPasswordField = false,
                        )
                        Spacer(modifier = Modifier.height(spacerHeight))
                        LoginTextField(
                            label = "Last Name",
                            value = lastName,
                            onValueChange = { viewModel.lastName.value = it },
                            isPasswordField = false,
                        )
                        Spacer(modifier = Modifier.height(spacerHeight))
                        LoginTextField(
                            label = "E-mail",
                            value = email,
                            onValueChange = { viewModel.email.value = it },
                            isPasswordField = false,
                        )
                        Spacer(modifier = Modifier.height(spacerHeight))

                        // Register user
                        OutlineBouncingButton(
                            modifier = Modifier,
                            inputText = "Register new ${options[selectedIndex]}",
                            inputIcon = Icons.Rounded.CheckCircle,
                            contentColor = success,
                            borderColor = success
                        ) {
                            if (validateInput() == "")
                                openDialog.value = true
                            else {
                                openDialog.value = false
                                viewModel.message.value = validateInput()
                            }
                        }
                        if (openDialog.value) {
                            DialogComponent(
                                onDismissRequest = { openDialog.value = false },
                                onConfirmation = {
                                    try {
                                        viewModel.role.value = Role(options[selectedIndex])
                                        val userData = RegistrationBody(
                                            username,
                                            password,
                                            name,
                                            lastName,
                                            email,
                                            viewModel.role.value!!
                                        )

                                        isAwaitingResponse = true

                                        viewModel.register(
                                            registrationHandler,
                                            userData,
                                            onSuccessfulRegistration = {
                                                isAwaitingResponse = false
                                                openDialog.value = false
                                                viewModel.username.value = ""
                                                viewModel.password.value = ""
                                                viewModel.firstName.value = ""
                                                viewModel.lastName.value = ""
                                                viewModel.email.value = ""
                                            },
                                            onFailedRegistration = {
                                                isAwaitingResponse = false
                                                openDialog.value = false
                                            }
                                        )
                                        println("Registered new ${options[selectedIndex]}")
                                        // Here goes the registration to the next layer, frontend done.
                                    } catch (e: Exception) {
                                        Log.e(
                                            "RegistrationError",
                                            "Error during registration: ${e.message}"
                                        )
                                        isAwaitingResponse = false
                                    }
                                },
                                dialogTitle = "Register new ${options[selectedIndex]}",
                                dialogText =
                                "" +
                                        "Are you sure you want to register ${name}, " +
                                        "${lastName} as role: ${options[selectedIndex]}?" +
                                        "",
                                iconTop = Icons.Rounded.CheckCircle,
                                highlightColor = success,
                                containerColor = BGLevelTwo
                            )
                        }
                        if (showMessage) {
                            Text(
                                text = message,
                                color = if (message.contains("success")) Color.Green else Color.Red,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }
}