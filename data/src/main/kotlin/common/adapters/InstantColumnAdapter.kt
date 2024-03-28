package common.adapters

import app.cash.sqldelight.ColumnAdapter
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField


object TimestampColumnAdapter : ColumnAdapter<Instant, OffsetDateTime> {
    override fun decode(databaseValue: OffsetDateTime): Instant {
        return databaseValue.toInstant().toKotlinInstant()
    }

    override fun encode(value: Instant): OffsetDateTime {
        return OffsetDateTime.from(value.toJavaInstant())
    }
}
