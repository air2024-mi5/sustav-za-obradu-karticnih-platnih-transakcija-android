package foi.air.szokpt.views.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
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
import foi.air.szokpt.models.AccountListRole
import foi.air.szokpt.models.ListedAccountInformation
import foi.air.szokpt.models.Transaction
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.components.interactible_components.FillBouncingButton
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.components.transaction_components.TransactionIcon
import foi.air.szokpt.ui.theme.Alternative
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.BGLevelThree
import foi.air.szokpt.ui.theme.BGLevelTwo
import foi.air.szokpt.ui.theme.BGLevelZeroLow
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.Secondary
import foi.air.szokpt.ui.theme.TextGray
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode
import foi.air.szokpt.views.ROUTE_ACCOUNT
import foi.air.szokpt.views.ROUTE_DAILY_PROCESS
import foi.air.szokpt.views.ROUTE_REGISTRATION

@Composable
fun AccountView(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = "Account",
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
                // Search
            }
            item(span = { GridItemSpan(2) }) {
                RegisterNewAccount(navController)
            }
            item(span = { GridItemSpan(2) }) {
                AccountList(navController)
            }

        }
    }
}
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
                            shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountList(navController: NavController) {

    TileSegment(
        tileSizeMode = TileSizeMode.WRAP_CONTENT,
        innerPadding = 10.dp,
        outerMargin = 4.dp,
        minWidth = 250.dp,
        minHeight = 20.dp,
        color = BGLevelOne
    ) {
        var active by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "All Accounts",
                    color = TextWhite,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
                OutlineBouncingButton(
                    modifier = Modifier,
                    inputText = "",
                    inputIcon = Icons.AutoMirrored.Rounded.ArrowForward,
                    contentColor = Primary,
                    borderColor = Secondary,
                ) {
                    navController.navigate(ROUTE_ACCOUNT)
                }
            }
            SearchBarForAccount()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarForAccount() {
    var searchQuery by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val allAccounts = listOf(
        ListedAccountInformation("Alice", "Bob", "abob", AccountListRole.User),
        ListedAccountInformation("Antonio", "Testic", "test", AccountListRole.Admin),
        ListedAccountInformation("Matija", "Rosevelt", "mmatija", AccountListRole.User),
        ListedAccountInformation("Bob", "Taylor", "btaylor", AccountListRole.User),
        ListedAccountInformation("Alice", "Bob", "abob", AccountListRole.User),
        ListedAccountInformation("Antonio", "Testic", "test", AccountListRole.Admin),
        ListedAccountInformation("Matija", "Rosevelt", "mmatija", AccountListRole.User),
        ListedAccountInformation("Bob", "Taylor", "btaylor", AccountListRole.User)
    )
    val filteredAccounts = allAccounts.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.lastName.contains(searchQuery, ignoreCase = true) ||
                it.userName.contains(searchQuery, ignoreCase = true)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp, max = 400.dp)
    ) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = { /* Handle search submission logic --HERE-- */ },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text("Search accounts...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Close, contentDescription = null)
                    }
                }
            },
            colors = SearchBarDefaults.colors(
                containerColor = BGLevelTwo,
                dividerColor = BGLevelThree
            ),
            modifier = Modifier
                .fillMaxWidth() // Ensure it spans the full width - Else ERROR
        ) {
            // Display results using AccountListItem composable
            if (active) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    if (filteredAccounts.isNotEmpty()) {
                        filteredAccounts.forEach { account ->
                            AccountListItem(account = account)
                        }
                    } else {
                        Text(
                            text = "No results found :(",
                            color = TextGray,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AccountListItem(account: ListedAccountInformation) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 6.dp)
            .background(
                color = BGLevelThree,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { println("Selected account: $account") } // Forward HERE
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = account.role.name + " @" + account.userName,
                color = TextGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "${account.name} ${account.lastName}",
                color = TextWhite,
                fontSize = 16.sp
            )
        }
    }
}

