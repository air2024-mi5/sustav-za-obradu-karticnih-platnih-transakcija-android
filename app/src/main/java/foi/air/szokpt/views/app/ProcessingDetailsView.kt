package foi.air.szokpt.views.app

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import foi.air.szokpt.helpers.DateFormatter
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.Secondary
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.viewmodels.ProcessingDetailsViewModel
import hr.foi.air.szokpt.core.file_generation.ClearingFileGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ProcessingDetailsView(
    navController: NavController,
    clearingFileGenerators: Map<String, ClearingFileGenerator>,
) {
    val viewModel: ProcessingDetailsViewModel = viewModel()
    val errorMessage by viewModel.errorMessage.observeAsState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Latest Processing",
            color = TextWhite,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Surface(
            modifier = Modifier.wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            color = BGLevelOne.copy(alpha = 0.5f),
            border = BorderStroke(1.dp, TextWhite.copy(alpha = 0.1f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                viewModel.fetchLatestProcessing()
                when (errorMessage) {
                    null -> {
                        val latestProcessing = viewModel.latestProcessing.observeAsState().value
                        if (latestProcessing != null) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.padding(start = 5.dp)
                            ) {
                                Text(
                                    text = "Status: ${latestProcessing.status}",
                                    color = TextWhite,
                                    fontSize = 15.sp
                                )

                                Text(
                                    text = "Scheduled at: ${
                                        latestProcessing.scheduledAt?.let {
                                            DateFormatter.format(
                                                it
                                            )
                                        }
                                    }",
                                    color = TextWhite,
                                    fontSize = 15.sp
                                )

                                Text(
                                    text = "Processed at: ${
                                        latestProcessing.processedAt?.let {
                                            DateFormatter.format(
                                                it
                                            )
                                        }
                                    }",
                                    color = TextWhite,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "Transactions processed: ${latestProcessing.processedTransactionsCount}",
                                    color = TextWhite,
                                    fontSize = 15.sp
                                )
                            }
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                clearingFileGenerators.entries.chunked(2).forEach { chunk ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        chunk.forEach { (label, generator) ->
                                            OutlineBouncingButton(
                                                modifier = Modifier.weight(1f),
                                                inputText = label,
                                                inputIcon = Icons.AutoMirrored.Filled.ExitToApp,
                                                contentColor = Primary,
                                                borderColor = Secondary,
                                                fontSize = 14.sp,
                                                spacer = 10.dp
                                            ) {
                                                viewModel.viewModelScope.launch {
                                                    withContext(Dispatchers.IO) {
                                                        generator.generateFile(
                                                            processingRecord = latestProcessing,
                                                            fileName = "processing_report_" +
                                                                    latestProcessing.processedAt?.let {
                                                                        DateFormatter.format(it)
                                                                    }
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
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