package io.github.santimattius.android.deeplink.watcher.internal.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "deeplink")
data class DeeplinkEntity(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "uri") val uri: String,
    @ColumnInfo(name = "referrer") val referrer: String?,
    @ColumnInfo(name = "createAt") val createAt: Date,
)
