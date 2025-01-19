package foi.air.szokpt.models

import java.util.Date

data class LatestProcess(
    val processNumber: Int,
    val processDate: Date,
    val fullProcessId: Int
)