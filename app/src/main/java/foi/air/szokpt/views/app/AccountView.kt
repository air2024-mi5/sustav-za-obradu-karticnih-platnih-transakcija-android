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
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import foi.air.szokpt.helpers.AccountUpdateHandler
import foi.air.szokpt.ui.components.StyledTextField
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.components.accounts_components.accountView.BlockAccountButton
import foi.air.szokpt.ui.components.accounts_components.accountView.BlockAccountDialog
import foi.air.szokpt.ui.components.accounts_components.accountView.DeactivateAccountButton
import foi.air.szokpt.ui.components.accounts_components.accountView.DeactivateAccountDialog
import foi.air.szokpt.ui.components.accounts_components.accountView.EditConfirmationDialog
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode
import foi.air.szokpt.ui.theme.success
import foi.air.szokpt.ui.theme.warning
import foi.air.szokpt.viewmodels.AccountViewModel
import foi.air.szokpt.views.ROUTE_ALL_ACCOUNT_SEARCH
import hr.foi.air.szokpt.ws.models.responses.User

@Composable
fun AccountView(
    navController: NavController,
    providedAccount: User,
    viewModel: AccountViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.initializeUserAccountData(providedAccount)
    }

    val currentUserAccountData by viewModel.currentUserAccountData.observeAsState()
    val storedUserAccountData by viewModel.storedUserAccountData.observeAsState()
    val message by viewModel.message.observeAsState("")

    var isEditTileVisible by remember { mutableStateOf(false) }
    val openEditDialog = remember { mutableStateOf(false) }
    val openBlockDialog = remember { mutableStateOf(false) }
    val openDeactivateDialog = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item {
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
                                    imageVector = if (storedUserAccountData?.role?.name == "admin")
                                        Icons.Rounded.AccountCircle else Icons.Rounded.Person,
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
                                    storedUserAccountData?.let {
                                        Text(
                                            text = it.username,
                                            color = Primary,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 18.sp
                                        )
                                    }
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${storedUserAccountData?.firstName} ${storedUserAccountData?.lastName}",
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
                                                viewModel.clearMessage()
                                                isEditTileVisible = true
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = message,
                        color = Color.Red,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            item {
                if (isEditTileVisible) {
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
                                value = currentUserAccountData?.username ?: "",
                                onValueChange = { viewModel.setUsername(it) },
                                isPasswordField = false
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            StyledTextField(
                                label = "E-mail",
                                value = currentUserAccountData?.email ?: "",
                                onValueChange = { viewModel.setEmail(it) },
                                isPasswordField = false
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            StyledTextField(
                                label = "First Name",
                                value = currentUserAccountData?.firstName ?: "",
                                onValueChange = { viewModel.setFirstName(it) },
                                isPasswordField = false
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            StyledTextField(
                                label = "Last Name",
                                value = currentUserAccountData?.lastName ?: "",
                                onValueChange = { viewModel.setLastName(it) },
                                isPasswordField = false
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            StyledTextField(
                                label = "Password",
                                value = currentUserAccountData?.password ?: "",
                                onValueChange = { viewModel.setPassword(it) },
                                isPasswordField = true
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlineBouncingButton(
                                onClick = {
                                    isEditTileVisible = false
                                    viewModel.clearMessage()
                                    viewModel.resetUserAccountData()
                                },
                                contentColor = warning,
                                borderColor = warning,
                                inputIcon = Icons.Rounded.KeyboardArrowUp,
                                inputText = "Close",
                                modifier = Modifier
                            )
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    DeactivateAccountButton(openDeactivateDialog)
                    currentUserAccountData?.let { user ->
                        BlockAccountButton(openBlockDialog, user)
                    }
                }
            }
        }
        if (openEditDialog.value) {
            EditConfirmationDialog(
                openEditDialog = openEditDialog,
                user = currentUserAccountData!!,
                onConfirm = {
                    val isValid = viewModel.validateData(currentUserAccountData!!)
                    if (isValid) {
                        viewModel.updateAccountData(
                            accountUpdateHandler = AccountUpdateHandler(),
                            newUserData = currentUserAccountData!!
                        )
                        isEditTileVisible = false
                    }
                },
                onDismiss = {
                    viewModel.resetUserAccountData()
                    isEditTileVisible = false
                }
            )
        }
        if (openDeactivateDialog.value) {
            DeactivateAccountDialog(
                openDeactivateDialog = openDeactivateDialog,
                user = storedUserAccountData!!,
                onConfirm = {
                    viewModel.setDeactivatedStatus(!storedUserAccountData!!.deactivated)
                    viewModel.updateAccountData(
                        accountUpdateHandler = AccountUpdateHandler(),
                        newUserData = storedUserAccountData!!
                    )
                    navController.navigate(ROUTE_ALL_ACCOUNT_SEARCH)
                }
            )
        }
        if (openBlockDialog.value) {
            BlockAccountDialog(
                openBlockDialog = openBlockDialog,
                user = storedUserAccountData!!,
                onConfirm = {
                    viewModel.setBlockedStatus(!storedUserAccountData!!.blocked)
                    viewModel.updateAccountData(
                        accountUpdateHandler = AccountUpdateHandler(),
                        newUserData = storedUserAccountData!!
                    )
                }
            )
        }
    }
}
