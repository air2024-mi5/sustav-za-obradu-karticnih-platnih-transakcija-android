package foi.air.szokpt.views.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.BGLevelZeroHigh
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.Secondary
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode
import foi.air.szokpt.utils.DateFormatter
import foi.air.szokpt.viewmodels.ProcessingDetailsViewModel
import foi.air.szokpt.views.ROUTE_PREVIOUS_PROCESSINGS
import foi.air.szokpt.views.ROUTE_PROCESSING_DETAILS
import foi.air.szokpt.views.ROUTE_TRANSACTIONS_CANDIDATES
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun DailyProcessesDashboardView(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                TileSegment(
                    tileSizeMode = TileSizeMode.WRAP_CONTENT,
                    innerPadding = 12.dp,
                    outerMargin = 4.dp,
                    minWidth = 250.dp,
                    minHeight = 20.dp,
                    color = BGLevelZeroHigh
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Processing starts at 12 AM",
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 18.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            item(span = { GridItemSpan(2) }) {
                TileSegment(
                    tileSizeMode = TileSizeMode.WRAP_CONTENT,
                    innerPadding = 10.dp,
                    outerMargin = 4.dp,
                    minWidth = 250.dp,
                    minHeight = 20.dp,
                    color = BGLevelOne
                ) {
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
                                text = "Transactions Candidates",
                                color = TextWhite,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            OutlineBouncingButton(
                                modifier = Modifier,
                                inputText = "",
                                inputIcon = Icons.AutoMirrored.Rounded.ArrowForward,
                                contentColor = Primary,
                                borderColor = Secondary,
                            ) {
                                navController.navigate(ROUTE_TRANSACTIONS_CANDIDATES)
                            }
                        }
                    }
                }
            }
            item(span = { GridItemSpan(2) }) {
                TileSegment(
                    tileSizeMode = TileSizeMode.WRAP_CONTENT,
                    innerPadding = 15.dp,
                    outerMargin = 4.dp,
                    minWidth = 250.dp,
                    minHeight = 20.dp,
                    color = BGLevelOne
                ) {
                    val viewModel: ProcessingDetailsViewModel = viewModel()
                    val errorMessage by viewModel.errorMessage.observeAsState()

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(7.dp),
                            text = "Latest Processing",
                            color = TextWhite,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            viewModel.fetchLatestProcessing()
                            when (errorMessage) {
                                null -> {
                                    val latestProcessing =
                                        viewModel.processingRecord.observeAsState().value
                                    if (latestProcessing != null) {
                                        Column(
                                            modifier = Modifier
                                                .padding(start = 15.dp),
                                            verticalArrangement = Arrangement.Top
                                        ) {

                                            Text(
                                                text = "Processed on:",
                                                color = TextWhite,
                                                fontSize = 15.sp
                                            )
                                            Text(
                                                text = "${
                                                    latestProcessing.processedAt?.let {
                                                        DateFormatter.format(
                                                            it
                                                        )
                                                    }
                                                }",
                                                color = TextWhite,
                                                fontSize = 15.sp
                                            )

                                        }
                                        OutlineBouncingButton(
                                            modifier = Modifier.width(100.dp),
                                            inputText = "",
                                            inputIcon = Icons.AutoMirrored.Rounded.ArrowForward,
                                            contentColor = Primary,
                                            borderColor = Secondary,
                                        ) {
                                            val revertible: Boolean = true
                                            val gson = Gson()
                                            val processingJson = gson.toJson(latestProcessing)
                                            val encodedProcessingJson = URLEncoder.encode(
                                                processingJson,
                                                StandardCharsets.UTF_8.toString()
                                            )

                                            navController.navigate("$ROUTE_PROCESSING_DETAILS/$encodedProcessingJson?revertable=$revertible")
                                        }
                                    } else {
                                        Text(
                                            text = "No data about last processing.",
                                            color = TextWhite,
                                            fontSize = 15.sp
                                        )
                                    }
                                }

                                else -> {
                                    Text(
                                        text = errorMessage!!,
                                        color = TextWhite,
                                        fontSize = 15.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
            item(span = { GridItemSpan(2) }) {
                TileSegment(
                    tileSizeMode = TileSizeMode.WRAP_CONTENT,
                    innerPadding = 10.dp,
                    outerMargin = 4.dp,
                    minWidth = 250.dp,
                    minHeight = 20.dp,
                    color = BGLevelOne
                ) {
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
                                text = "Previous Processings",
                                color = TextWhite,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            OutlineBouncingButton(
                                modifier = Modifier,
                                inputText = "",
                                inputIcon = Icons.AutoMirrored.Rounded.ArrowForward,
                                contentColor = Primary,
                                borderColor = Secondary,
                            ) {
                                navController.navigate(ROUTE_PREVIOUS_PROCESSINGS)
                            }
                        }
                    }
                }
            }
        }
    }
}