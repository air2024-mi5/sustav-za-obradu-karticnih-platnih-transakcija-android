package foi.air.szokpt

import hr.foi.air.szokpt.core.file_generation.ClearingFileGenerator
import hr.foi.air.szokpt.core.file_generation.FileSaver
import hr.foi.air.szokpt.core.processing.BatchRecord
import hr.foi.air.szokpt.core.processing.ProcessingRecord
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class ExcelClearingFileGenerator(private val fileSaver: FileSaver) : ClearingFileGenerator {
    override suspend fun generateFile(processingRecord: ProcessingRecord, fileName: String) {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet(fileName)

        var rowNum = 0
        var dataRow: XSSFRow
        for (batchRecord: BatchRecord in processingRecord.batchRecords) {
            dataRow = sheet.createRow(rowNum)
            dataRow.createCell(0).setCellValue(batchRecord.batchHeader)
            rowNum++
            dataRow = sheet.createRow(rowNum)
            dataRow.createCell(0).setCellValue(batchRecord.terminalParameterRecord)
            rowNum++
            for (transactionRecord: String in batchRecord.transactionRecords) {
                dataRow = sheet.createRow(rowNum)
                dataRow.createCell(0).setCellValue(transactionRecord)
                rowNum++
            }
            dataRow = sheet.createRow(rowNum)
            dataRow.createCell(0).setCellValue(batchRecord.batchTrailer)
            rowNum++
        }
        dataRow = sheet.createRow(rowNum)
        dataRow.createCell(0).setCellValue(56.toString())
        fileSaver.saveFile(
            "Documents/SZOKPT",
            fileName,
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        ) { outputStream ->
            workbook.write(outputStream)
        }
    }
}