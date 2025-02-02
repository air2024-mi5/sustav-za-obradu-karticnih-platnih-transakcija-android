package foi.air.szokpt.pdf

import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import hr.foi.air.szokpt.core.file_generation.ClearingFileGenerator
import hr.foi.air.szokpt.core.file_generation.FileSaver
import hr.foi.air.szokpt.core.processing.BatchRecord
import hr.foi.air.szokpt.core.processing.ProcessingRecord


class PdfClearingFileGenerator(private val fileSaver: FileSaver) : ClearingFileGenerator {
    override suspend fun generateFile(processingRecord: ProcessingRecord, fileName: String) {
        fileSaver.saveFile(
            "Documents/SZOKPT",
            fileName,
            "application/pdf"
        ) { outputStream ->
            val pdfWriter = PdfWriter(outputStream)
            val pdfDocument = PdfDocument(pdfWriter)
            val document = Document(pdfDocument).setFontSize(10f)

            for (batchRecord: BatchRecord in processingRecord.batchRecords) {
                document.add(Paragraph(batchRecord.batchHeader))
                document.add(Paragraph(batchRecord.terminalParameterRecord))
                for (transactionRecord: String in batchRecord.transactionRecords) {
                    document.add(Paragraph(transactionRecord))
                }
                document.add(Paragraph(batchRecord.batchTrailer))
            }
            document.add(Paragraph(56.toString()))
            document.close()
        }
    }
}