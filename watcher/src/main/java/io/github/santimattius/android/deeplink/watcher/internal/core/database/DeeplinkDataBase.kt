package io.github.santimattius.android.deeplink.watcher.internal.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.santimattius.android.deeplink.watcher.internal.core.database.converters.DateConverter


@Database(entities = [DeeplinkEntity::class], version = 1)
@TypeConverters(DateConverter::class)
internal abstract class DeeplinkDataBase : RoomDatabase() {

    abstract fun deeplinkDao(): DeeplinkDao

    companion object {
        private const val DB_NAME = "deeplink_watcher_db"
        fun get(applicationContext: Context): DeeplinkDataBase {
            return Room.databaseBuilder(
                applicationContext,
                DeeplinkDataBase::class.java, DB_NAME
            ).build()

        }
    }
}