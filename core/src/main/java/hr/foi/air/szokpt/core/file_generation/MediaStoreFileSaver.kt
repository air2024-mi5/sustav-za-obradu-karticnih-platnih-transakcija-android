package hr.foi.air.szokpt.core.file_generation

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import java.io.OutputStream

class MediaStoreFileSaver(
    private val context: Context,
    private val outcomeListener: FileSavingOutcomeListener,
) : FileSaver {

    override suspend fun saveFile(
        directory: String,
        fileName: String,
        mimeType: String,
        writeToStream: (OutputStream) -> Unit,
    ) {
        try {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                put(MediaStore.MediaColumns.RELATIVE_PATH, directory)
            }

            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)


            uri?.let { contentUri ->
                resolver.openOutputStream(contentUri)?.use { outputStream ->
                    writeToStream(outputStream)
                }
                openDocument(uri, mimeType)
            } ?: run {
                outcomeListener.onFailedSaving()
            }
        } catch (ex: Exception) {
            outcomeListener.onFailedSaving()
        }
    }

    private fun openDocument(uri: Uri, mimeType: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, mimeType)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_GRANT_READ_URI_PERMISSION
        }

        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            outcomeListener.onFailedFileOpening()
        }

    }
}