package common.adapters

import com.squareup.sqldelight.ColumnAdapter
import kotlinx.datetime.Instant
import kotlinx.datetime.toKotlinInstant
import java.time.ZoneId
import java.time.format.DateTimeFormatterBuilder


object TimestampColumnAdapter : ColumnAdapter<Instant, String> {
    private val dateTimeFormat = DateTimeFormatterBuilder()
        .appendPattern("yyyy-MM-dd HH:mm:ss")
        .toFormatter()
        .withZone(ZoneId.of("UTC"))

    override fun decode(databaseValue: String): Instant = dateTimeFormat.parse(
        databaseValue,
        java.time.Instant::from,
    ).toKotlinInstant()

    override fun encode(value: Instant): String = value.toString()
}
