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
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import foi.air.szokpt.helpers.DateFormatter
import foi.air.szokpt.ui.components.clearing_file_generation.GenerationButtonsRow
import foi.air.szokpt.ui.components.dialog_components.DialogComponent
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.danger
import foi.air.szokpt.viewmodels.ProcessingDetailsViewModel
import hr.foi.air.szokpt.core.file_generation.ClearingFileGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ProcessingDetailsView(
    clearingFileGenerators: Map<String, ClearingFileGenerator>,
    revertible: Boolean
) {
    val viewModel: ProcessingDetailsViewModel = viewModel()
    val errorMessage by viewModel.errorMessage.observeAsState()
    val coroutineScope = rememberCoroutineScope()

    val openRevertProcessingDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
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
                        val processingRecord = viewModel.latestProcessing.observeAsState().value
                        if (processingRecord != null) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.padding(start = 5.dp)
                            ) {
                                Text(
                                    text = "Status: ${processingRecord.status}",
                                    color = TextWhite,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "Scheduled at: ${
                                        processingRecord.scheduledAt?.let {
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
                                        processingRecord.processedAt?.let {
                                            DateFormatter.format(
                                                it
                                            )
                                        }
                                    }",
                                    color = TextWhite,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "Transactions processed: ${processingRecord.processedTransactionsCount}",
                                    color = TextWhite,
                                    fontSize = 15.sp
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    if (processingRecord.status == "COMPLETED" && revertible) {
                                        OutlineBouncingButton(
                                            onClick = {
                                                openRevertProcessingDialog.value = true
                                            },
                                            contentColor = danger,
                                            borderColor = danger,
                                            inputIcon = Icons.Rounded.Refresh,
                                            inputText = "Revert processing",
                                            modifier = Modifier
                                        )
                                    }
                                }
                            }
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                if (processingRecord.status == "COMPLETED") {
                                    clearingFileGenerators.entries.chunked(2).forEach { chunk ->
                                        GenerationButtonsRow(
                                            chunk,
                                            processingRecord
                                        ) { generator, fileName ->
                                            coroutineScope.launch(Dispatchers.IO) {
                                                generator.generateFile(
                                                    processingRecord = processingRecord,
                                                    fileName = fileName
                                                )
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
        if (openRevertProcessingDialog.value) {
            DialogComponent(
                dialogTitle = "Revert processing",
                dialogText = "Are you sure you want to permanently revert transaction processing?\n",
                highlightColor = danger,
                titleColor = TextWhite,
                iconTop = Icons.Rounded.Refresh,
                onConfirmation = {
                    viewModel.revertLastProcessing()
                    openRevertProcessingDialog.value = false
                },
                onDismissRequest = { openRevertProcessingDialog.value = false },
            )
        }
    }
}