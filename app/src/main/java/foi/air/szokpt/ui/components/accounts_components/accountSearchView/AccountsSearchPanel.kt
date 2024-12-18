package foi.air.szokpt.ui.components.accounts_components.accountSearchView

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.google.gson.Gson
import foi.air.szokpt.ui.theme.AppBorderRadius
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.BGLevelTwo
import foi.air.szokpt.ui.theme.TextGray
import foi.air.szokpt.viewmodels.AccountsViewModel
import foi.air.szokpt.views.ROUTE_USER_ACCOUNT_OVERVIEW
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsSearchPanel(navController: NavController, viewModel: AccountsViewModel) {
    viewModel.fetchUsers()

    var searchQuery by remember { mutableStateOf("") }
    val users by viewModel.accounts.observeAsState(emptyList())
    var active by remember { mutableStateOf(false) }
    val loading by viewModel.loading.observeAsState(true)
    val message by viewModel.message.observeAsState("")

    val filteredAccounts = users.filter {
        it.firstName.contains(searchQuery, ignoreCase = true) ||
                it.lastName.contains(searchQuery, ignoreCase = true) ||
                it.username.contains(searchQuery, ignoreCase = true)
    }

    if (loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(AppBorderRadius))
            .heightIn(min = 56.dp, max = 900.dp),
        contentAlignment = Alignment.TopStart
    ) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = {},
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
                dividerColor = BGLevelTwo,
                inputFieldColors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White
                )
            ),
            modifier = Modifier
                .fillMaxWidth(),
        )
        {
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
                                AccountListItem(
                                    account = account,
                                    onClick = {
                                        val gson = Gson()
                                        val userJson =
                                            gson.toJson(account)
                                        val encodedUserJson = URLEncoder.encode(
                                            userJson,
                                            StandardCharsets.UTF_8.toString()
                                        )
                                        navController.navigate(ROUTE_USER_ACCOUNT_OVERVIEW + "/$encodedUserJson")
                                    }
                                )
                            }
                        }

                    } else if (message.isNotEmpty()) {
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
