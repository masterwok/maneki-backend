package common.adapters

import com.squareup.sqldelight.ColumnAdapter
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import java.time.ZoneId
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField


object TimestampColumnAdapter : ColumnAdapter<Instant, String> {
    private val dateTimeFormat = DateTimeFormatterBuilder()
        .appendPattern("yyyy-MM-dd HH:mm:ss")
        .appendFraction(ChronoField.MILLI_OF_SECOND, 0, 3, true)
        .toFormatter()
        .withZone(ZoneId.of("UTC"))

    override fun decode(databaseValue: String): Instant = dateTimeFormat.parse(
        databaseValue,
        java.time.Instant::from,
    ).toKotlinInstant()

    override fun encode(value: Instant): String {
        return dateTimeFormat.format(value.toJavaInstant())
    }
}
