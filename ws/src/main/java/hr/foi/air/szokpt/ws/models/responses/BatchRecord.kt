package hr.foi.air.szokpt.ws.models.responses

import hr.foi.air.szokpt.core.processing.BatchRecord as CoreBatchRecord

fun CoreBatchRecord.toCoreBatchRecord(): CoreBatchRecord {
    return CoreBatchRecord(
        batchHeader = this.batchHeader,
        terminalParameterRecord = this.terminalParameterRecord,
        batchTrailer = this.batchTrailer,
        transactionRecords = this.transactionRecords
    )
}