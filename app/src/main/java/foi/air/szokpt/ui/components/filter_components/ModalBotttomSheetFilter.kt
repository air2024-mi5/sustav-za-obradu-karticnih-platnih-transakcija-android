package foi.air.szokpt.ui.components.filter_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foi.air.szokpt.R
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.danger
import foi.air.szokpt.ui.theme.success

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheetFilter(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    hasFilters: Boolean,
    isShowingFilters: Boolean,
    onShowFilterOptions: () -> Unit,
    onRemoveFilters: () -> Unit,
    filterOptionsContent: @Composable () -> Unit
) {
    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            containerColor = BGLevelOne,
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!isShowingFilters) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(9.dp)
                                .clickable {
                                    onRemoveFilters()
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.rounded_filter_alt_24),
                                contentDescription = "Add Filters Icon",
                                tint = success,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                text = if (hasFilters) "Change Current Filter" else "Apply New Filter",
                                fontSize = 18.sp,
                                color = success,
                                modifier = Modifier
                                    .clickable {
                                        onShowFilterOptions()
                                    }
                            )
                        }
                    }

                    if (hasFilters) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(9.dp)
                                    .clickable {
                                        onRemoveFilters()
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.rounded_filter_alt_off_24),
                                    contentDescription = "Remove Filters Icon",
                                    tint = danger,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = "Remove Filters",
                                    fontSize = 18.sp,
                                    color = danger
                                )
                            }
                        }
                    }
                } else {
                    item {
                        filterOptionsContent()
                    }
                }
            }
        }
    }
}