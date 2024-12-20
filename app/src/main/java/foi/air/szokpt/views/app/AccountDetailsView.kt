package foi.air.szokpt.views.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import foi.air.szokpt.ui.components.StyledTextField
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.components.dialog_components.DialogComponent
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.BGLevelTwo
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode
import foi.air.szokpt.ui.theme.danger
import foi.air.szokpt.ui.theme.success
import foi.air.szokpt.ui.theme.warning
import foi.air.szokpt.viewmodels.AccountDetailsViewModel
import hr.foi.air.szokpt.ws.models.responses.User

@Composable
fun UserAccountView(navController: NavController, providedAccount: User) {
    val viewModel: AccountDetailsViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.initializeAccountData(providedAccount)
    }

    val username by viewModel.username.observeAsState("")
    val name by viewModel.firstName.observeAsState("")
    val lastName by viewModel.lastName.observeAsState("")
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")

    var isEditTileVisible by remember { mutableStateOf(false) }
    var openEditDialog = remember { mutableStateOf(false) }
    var openBlockDialog = remember { mutableStateOf(false) }
    var openDeactivateDialog = remember { mutableStateOf(false) }
    val isEditConfirmed = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        AccountHeader()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item {
                UserOverviewTile(
                    providedAccount,
                    isEditTileVisible,
                    { isEditTileVisible = it },
                    openEditDialog
                )
            }
            item {
                if (isEditTileVisible) EditTile(
                    viewModel,
                    username,
                    password,
                    email,
                    name,
                    lastName,
                    { isEditTileVisible = false })
            }
            item { ActionButtons(openDeactivateDialog, openBlockDialog) }
        }
        if (openEditDialog.value) {
            EditConfirmationDialog(openEditDialog, name, lastName) {
                isEditTileVisible = false
                isEditConfirmed.value = true
            }
        }
        if (openDeactivateDialog.value) {
            DeactivateAccountDialog(openDeactivateDialog, name, lastName, username)
        }
        if (openBlockDialog.value) {
            BlockAccountDialog(openBlockDialog, name, lastName, username)
        }
    }
}

@Composable
fun AccountHeader() {
    Text(
        modifier = Modifier.padding(16.dp),
        text = "Account Overview",
        color = TextWhite,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun UserOverviewTile(
    providedAccount: User,
    isEditTileVisible: Boolean,
    onEditTileToggle: (Boolean) -> Unit,
    openEditDialog: MutableState<Boolean>
) {
    TileSegment(
        tileSizeMode = TileSizeMode.WRAP_CONTENT,
        innerPadding = 8.dp,
        outerMargin = 0.dp,
        minWidth = 250.dp,
        minHeight = 90.dp,
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(BGLevelOne, Primary),
                        startY = 0f,
                        endY = 1600f
                    )
                )
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = if (providedAccount.role.name == "admin") Icons.Rounded.AccountCircle else Icons.Rounded.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(68.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = BGLevelOne,
                                shape = RoundedCornerShape(30.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "@${providedAccount.username}",
                            color = Primary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${providedAccount.firstName} ${providedAccount.lastName}",
                            color = TextWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.weight(1f)
                        )
                        OutlineBouncingButton(
                            onClick = {
                                if (isEditTileVisible) {
                                    openEditDialog.value = true
                                } else {
                                    onEditTileToggle(true)
                                }
                            },
                            contentColor = if (isEditTileVisible) success else TextWhite,
                            borderColor = if (isEditTileVisible) success else TextWhite,
                            inputIcon = if (isEditTileVisible) Icons.Rounded.Refresh else Icons.Rounded.Edit,
                            inputText = if (isEditTileVisible) "Save" else "",
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EditTile(
    viewModel: AccountDetailsViewModel,
    username: String,
    password: String,
    email: String,
    name: String,
    lastName: String,
    onClose: () -> Unit
) {
    TileSegment(
        tileSizeMode = TileSizeMode.FILL_MAX_WIDTH,
        innerPadding = 8.dp,
        outerMargin = 8.dp,
        minWidth = 250.dp,
        color = BGLevelOne
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            StyledTextField(
                label = "Username",
                value = username,
                onValueChange = { viewModel.updateUsername(it) },
                isPasswordField = false
            )
            Spacer(modifier = Modifier.height(12.dp))
            StyledTextField(
                label = "E-mail",
                value = email,
                onValueChange = { viewModel.updateEmail(it) },
                isPasswordField = false
            )
            Spacer(modifier = Modifier.height(12.dp))
            StyledTextField(
                label = "Name",
                value = name,
                onValueChange = { viewModel.updateFirstName(it) },
                isPasswordField = false
            )
            Spacer(modifier = Modifier.height(12.dp))
            StyledTextField(
                label = "Last Name",
                value = lastName,
                onValueChange = { viewModel.updateLastName(it) },
                isPasswordField = false
            )
            Spacer(modifier = Modifier.height(12.dp))
            StyledTextField(
                label = "Password",
                value = password,
                onValueChange = { viewModel.updatePassword(it) },
                isPasswordField = true
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlineBouncingButton(
                onClick = onClose,
                contentColor = warning,
                borderColor = warning,
                inputIcon = Icons.Rounded.KeyboardArrowUp,
                inputText = "Close",
                modifier = Modifier
            )
        }
    }
}

@Composable
fun ActionButtons(
    openDeactivateDialog: MutableState<Boolean>,
    openBlockDialog: MutableState<Boolean>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlineBouncingButton(
            onClick = { openDeactivateDialog.value = true },
            inputText = "Deactivate Acc.",
            contentColor = danger,
            borderColor = danger,
            inputIcon = Icons.Rounded.Delete
        )
        OutlineBouncingButton(
            onClick = { openBlockDialog.value = true },
            inputText = "Block",
            contentColor = warning,
            borderColor = warning,
            inputIcon = Icons.Rounded.Clear
        )
    }
}

@Composable
fun EditConfirmationDialog(
    openEditDialog: MutableState<Boolean>,
    name: String,
    lastName: String,
    onConfirm: () -> Unit
) {
    DialogComponent(
        onDismissRequest = { openEditDialog.value = false },
        onConfirmation = onConfirm,
        dialogTitle = "Change user data?",
        dialogText = "Are you sure you want to change ${name} ${lastName}s data?",
        iconTop = Icons.Rounded.CheckCircle,
        highlightColor = success,
        containerColor = BGLevelTwo
    )
}

@Composable
fun DeactivateAccountDialog(
    openDeactivateDialog: MutableState<Boolean>,
    name: String,
    lastName: String,
    username: String
) {
    DialogComponent(
        onDismissRequest = { openDeactivateDialog.value = false },
        onConfirmation = { openDeactivateDialog.value = false },
        dialogTitle = "Deactivate User Account",
        dialogText = "Are you sure you want to DEACTIVATE ${name} ${lastName}, @${username}? \n \nBe cautious!",
        iconTop = Icons.Rounded.Delete,
        highlightColor = danger,
        containerColor = BGLevelTwo
    )
}

@Composable
fun BlockAccountDialog(
    openBlockDialog: MutableState<Boolean>,
    name: String,
    lastName: String,
    username: String
) {
    DialogComponent(
        onDismissRequest = { openBlockDialog.value = false },
        onConfirmation = { openBlockDialog.value = false },
        dialogTitle = "Block User Account",
        dialogText = "Are you sure you want to BLOCK ${name} ${lastName}, @${username}? \n \nBe cautious!",
        iconTop = Icons.Rounded.Close,
        highlightColor = warning,
        containerColor = BGLevelTwo
    )
}
