package foi.air.szokpt.ui.components.clearing_file_generation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.Secondary
import foi.air.szokpt.utils.DateFormatter
import hr.foi.air.szokpt.core.file_generation.ClearingFileGenerator
import hr.foi.air.szokpt.core.processing.ProcessingRecord

@Composable
fun GenerationButtonsRow(
    chunk: List<Map.Entry<String, ClearingFileGenerator>>,
    latestProcessing: ProcessingRecord,
    onClick: (ClearingFileGenerator, String) -> Unit
) {
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
                val fileName = "processing_report_" + (latestProcessing.processedAt?.let {
                    DateFormatter.format(it)
                } ?: "unknown")
                onClick(generator, fileName)
            }
        }
    }
}
