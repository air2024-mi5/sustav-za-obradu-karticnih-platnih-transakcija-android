package foi.air.szokpt.views.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import foi.air.szokpt.models.AccountListRole
import foi.air.szokpt.models.ListedAccountInformation
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.components.list_components.AccountListItem
import foi.air.szokpt.ui.theme.AppBorderRadius
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.BGLevelThree
import foi.air.szokpt.ui.theme.BGLevelTwo
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.Secondary
import foi.air.szokpt.ui.theme.TextGray
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode


@Composable
fun AccountSearchView(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = "Search All Accounts",
            color = TextWhite,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        TileSegment(
            tileSizeMode = TileSizeMode.WRAP_CONTENT,
            innerPadding = 0.dp,
            outerMargin = 8.dp,
            minWidth = 250.dp,
            minHeight = 90.dp,
            color = BGLevelOne
        ) {
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
            .clip(RoundedCornerShape(AppBorderRadius))
            .heightIn(min = 56.dp, max = 900.dp),
        contentAlignment = Alignment.TopStart // Align everything to the top
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
                containerColor = BGLevelOne,
                dividerColor = BGLevelTwo
            ),
            modifier = Modifier
                .fillMaxWidth() // Ensure it spans the full width - Else ERROR
        ) {
            // Display results using AccountListItem composable
            if (active) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 900.dp)
                ) {
                    if (filteredAccounts.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            items(filteredAccounts) { account ->
                                AccountListItem(account = account)
                            }
                        }
                    } else {
                        Text(
                            text = "No results found :(",
                            color = TextGray,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}
