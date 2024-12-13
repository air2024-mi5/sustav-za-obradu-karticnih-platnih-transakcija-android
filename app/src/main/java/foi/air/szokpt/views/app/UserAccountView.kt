package foi.air.szokpt.views.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController
import foi.air.szokpt.helpers.SharedAccountViewModel
import foi.air.szokpt.models.AccountListRole
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode
import foi.air.szokpt.ui.theme.danger
import foi.air.szokpt.ui.theme.success
import foi.air.szokpt.ui.theme.warning

@Composable
fun UserAccountView(navController: NavController, sharedViewModel: SharedAccountViewModel){
    val account = sharedViewModel.selectedAccount
    var isEditTileVisible by remember { mutableStateOf(false) }
    if (account != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .padding(16.dp),
                text = "Account Overview",
                color = TextWhite,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
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
                                        endY = 1200f
                                    ),

                                )
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Role Icon Row
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(
                                        imageVector = if (account.role == AccountListRole.Admin) Icons.Rounded.AccountCircle else Icons.Rounded.Person,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(68.dp)
                                    )
                                }
                                Column (
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                color = BGLevelOne,
                                                shape = RoundedCornerShape(30.dp)
                                            )
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = "@${account.userName}",
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
                                            text = "${account.name} ${account.lastName}",
                                            color = TextWhite,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp,
                                            modifier = Modifier.weight(1f)
                                        )
                                        OutlineBouncingButton(
                                            onClick = {
                                                isEditTileVisible = !isEditTileVisible // Toggle visibility of the second tile
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
                if (isEditTileVisible) {
                    item {
                        TileSegment(
                            tileSizeMode = TileSizeMode.FILL_MAX_WIDTH,
                            innerPadding = 8.dp,
                            outerMargin = 8.dp,
                            minWidth = 250.dp,
                            minHeight = 90.dp,
                            color = BGLevelOne
                        ) {
                            Text(
                                text = "Additional Information",
                                color = TextWhite,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlineBouncingButton(
                            onClick = { /* Trigger account Deactivation HERE */ },
                            inputText = "Deactivate Acc.",
                            contentColor = danger,
                            borderColor = danger,
                            inputIcon = Icons.Rounded.Delete,
                        )
                        OutlineBouncingButton(
                            onClick = { /* Trigger account Blocking HERE */ },
                            inputText = "Block",
                            contentColor = warning,
                            borderColor = warning,
                            inputIcon = Icons.Rounded.Clear,
                        )
                    }

                }
            }
        }

    }
    else {
        Text("No account selected", color = TextWhite)
    }
}