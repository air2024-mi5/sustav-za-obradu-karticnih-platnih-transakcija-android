package foi.air.szokpt.views.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotApplyResult
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import foi.air.szokpt.ui.components.LoginTextField
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.components.dialog_components.DialogComponent
import foi.air.szokpt.ui.components.interactible_components.FillBouncingButton
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.BGLevelThree
import foi.air.szokpt.ui.theme.BGLevelTwo
import foi.air.szokpt.ui.theme.BGLevelZeroLow
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.Secondary
import foi.air.szokpt.ui.theme.TextBlack
import foi.air.szokpt.ui.theme.TextGray
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode
import foi.air.szokpt.ui.theme.success
import foi.air.szokpt.views.ROUTE_REGISTRATION

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationView(navController: NavController, userType: String) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val openDialog = remember { mutableStateOf(false) }

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
        ){
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
                        val options = listOf("User", "Admin")
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
                            onValueChange = { username = it },
                            isPasswordField = false,
                        )
                        Spacer(modifier = Modifier.height(spacerHeight))
                        LoginTextField(
                            label = "Password",
                            value = password,
                            onValueChange = { password = it },
                            isPasswordField = true,
                        )
                        Spacer(modifier = Modifier.height(spacerHeight))
                        LoginTextField(
                            label = "Name",
                            value = name,
                            onValueChange = { name = it },
                            isPasswordField = false,
                        )
                        Spacer(modifier = Modifier.height(spacerHeight))
                        LoginTextField(
                            label = "Last Name",
                            value = lastName,
                            onValueChange = { lastName = it },
                            isPasswordField = false,
                        )
                        Spacer(modifier = Modifier.height(spacerHeight))
                        LoginTextField(
                            label = "E-mail",
                            value = email,
                            onValueChange = { email = it },
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
                            openDialog.value = true
                        }
                        if (openDialog.value) {
                            DialogComponent(
                                onDismissRequest = { openDialog.value = false },
                                onConfirmation = {
                                    openDialog.value = false
                                    println("Registered new ${options[selectedIndex]}")

                                    // Here goes the registration to the next layer, frontend done.
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
                    }
                }
            }
        }

    }
}