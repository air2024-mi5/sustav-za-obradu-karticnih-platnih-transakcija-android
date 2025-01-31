package foi.air.szokpt.views.app

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import foi.air.szokpt.ui.components.IconMessage
import foi.air.szokpt.ui.components.pagination_components.Pagination
import foi.air.szokpt.ui.components.processing_components.ProcessingItem
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.viewmodels.ProcessingsViewModel
import foi.air.szokpt.views.ROUTE_PROCESSING_DETAILS
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PreviousProcessingsView(navController: NavController) {
    val viewModel: ProcessingsViewModel = viewModel()
    val processingPage by viewModel.processingPage.observeAsState()
    val currentPage by viewModel.currentPage.observeAsState()
    val totalPages by viewModel.totalPages.observeAsState()
    val processingRecords by viewModel.processingRecords.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(true)

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(currentPage) {
        if (processingPage == null) {
            viewModel.fetchProcessings(1)
        }

        coroutineScope.launch {
            listState.scrollToItem(0)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = "Previous Processings",
            color = TextWhite,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp)
        )

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Primary
                )
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .padding(horizontal = 0.dp)
                    .weight(1f)
            ) {
                if (processingPage?.processingList.isNullOrEmpty()) {
                    item {
                        IconMessage(
                            title = "No results found",
                            description = "No processing data found.",
                            icon = Icons.Rounded.Search
                        )
                    }
                }
                processingRecords?.forEach { processing ->
                    item {
                        ProcessingItem(
                            processing = processing,
                            onClick = {
                                val revertable = false
                                val gson = Gson()
                                val processingJson = gson.toJson(processing)
                                val encodedProcessingJson = URLEncoder.encode(
                                    processingJson,
                                    StandardCharsets.UTF_8.toString()
                                )

                                navController.navigate(
                                    "${ROUTE_PROCESSING_DETAILS}/$encodedProcessingJson?revertable=$revertable"
                                )
                            }
                        )
                    }
                }
            }
            Pagination(
                currentPage = currentPage,
                totalPages = totalPages,
                onPageSelected = { page ->
                    viewModel.fetchProcessings(page)
                    coroutineScope.launch {
                        listState.scrollToItem(0)
                    }
                }
            )
        }
    }
}
