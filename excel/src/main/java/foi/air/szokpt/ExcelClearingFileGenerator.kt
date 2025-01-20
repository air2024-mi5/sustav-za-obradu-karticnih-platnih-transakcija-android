package foi.air.szokpt

import hr.foi.air.szokpt.core.file_generation.ClearingFileGenerator
import hr.foi.air.szokpt.core.file_generation.FileSaver
import hr.foi.air.szokpt.core.processing.ProcessingRecord
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class ExcelClearingFileGenerator(private val fileSaver: FileSaver) : ClearingFileGenerator {
    override suspend fun generateFile(processingRecord: ProcessingRecord, fileName: String) {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("test")
        val dataRow1 = sheet.createRow(0)
        dataRow1.createCell(0).setCellValue("test")

        fileSaver.saveFile(
            "Documents/SZOKPT",
            "test",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        ) { outputStream ->
            workbook.write(outputStream)
        }
    }
}