package hr.foi.air.szokpt.core.file_generation

import java.io.OutputStream

interface FileSaver {
    suspend fun saveFile(
        directory: String,
        fileName: String,
        mimeType: String,
        writeToStream: (OutputStream) -> Unit,
    )
}