package hr.foi.air.szokpt.ws.models.responses

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

class TimestampType : TypeAdapter<Timestamp>() {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())

    override fun write(out: JsonWriter, value: Timestamp) {
        value.let {
            out.value(dateFormat.format(value))
        } ?: out.nullValue()
    }

    override fun read(input: JsonReader): Timestamp {
        val dateStr = input.nextString()
        return try {
            Timestamp(dateFormat.parse(dateStr)?.time ?: 0)
        } catch (e: Exception) {
            Timestamp(0)
        }
    }
}

val gson: Gson = GsonBuilder()
    .registerTypeAdapter(Timestamp::class.java, TimestampType())
    .create()