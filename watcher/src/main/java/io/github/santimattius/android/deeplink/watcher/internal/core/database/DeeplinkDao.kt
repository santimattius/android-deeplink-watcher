package io.github.santimattius.android.deeplink.watcher.internal.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

@Dao
interface DeeplinkDao {

    @Insert
    suspend fun insert(deeplink: DeeplinkEntity)

    @Delete
    suspend fun delete(deeplink: DeeplinkEntity)

}