package hr.foi.air.szokpt.core.processing

data class BatchRecord(
    val batchHeader: String,
    val terminalParameterRecord: String,
    val batchTrailer: String,
    val transactionRecords: List<String>
)