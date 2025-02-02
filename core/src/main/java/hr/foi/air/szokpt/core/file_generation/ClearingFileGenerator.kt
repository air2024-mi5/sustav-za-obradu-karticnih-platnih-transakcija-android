package hr.foi.air.szokpt.core.file_generation

import hr.foi.air.szokpt.core.processing.ProcessingRecord

interface ClearingFileGenerator {
    suspend fun generateFile(processingRecord: ProcessingRecord, fileName: String)
}