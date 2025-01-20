package hr.foi.air.szokpt.core.file_generation

interface FileSavingOutcomeListener {
    fun onFailedSaving()
    fun onFailedFileOpening()
}