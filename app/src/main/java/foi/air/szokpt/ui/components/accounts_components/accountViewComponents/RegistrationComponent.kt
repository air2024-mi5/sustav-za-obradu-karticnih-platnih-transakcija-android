package foi.air.szokpt.ui.components.accounts_components.accountViewComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.components.interactible_components.FillBouncingButton
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.BGLevelZeroLow
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.Secondary
import foi.air.szokpt.ui.theme.TextGray
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode
import foi.air.szokpt.views.ROUTE_REGISTRATION

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterNewAccount(navController: NavController) {
    TileSegment(
        tileSizeMode = TileSizeMode.WRAP_CONTENT,
        innerPadding = 12.dp,
        outerMargin = 4.dp,
        minWidth = 250.dp,
        minHeight = 20.dp,
        color = BGLevelOne
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "New Account",
                color = TextWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                var selectedIndex by remember { mutableStateOf(0) }
                val options = listOf("User", "Admin")
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
                FillBouncingButton(
                    modifier = Modifier,
                    inputText = "Register new ${options[selectedIndex]}",
                    inputIcon = Icons.Rounded.Add,
                    buttonColor = Primary,
                    contentColor = TextWhite,
                    useSpacerAnimation = true,
                    useIconAnimation = true
                ) {
                    navController.navigate(ROUTE_REGISTRATION + "/${options[selectedIndex]}")
                }
            }
        }
    }
}