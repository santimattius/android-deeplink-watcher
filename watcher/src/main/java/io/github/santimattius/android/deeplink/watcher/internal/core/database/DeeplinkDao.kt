package io.github.santimattius.android.deeplink.watcher.internal.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface DeeplinkDao {

    @Insert
    suspend fun insert(deeplink: DeeplinkEntity)

    @Query("DELETE FROM deeplink WHERE uuid =:id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM deeplink WHERE uri LIKE '%' || :text || '%' ORDER by createAt desc")
    fun search(text: String): Flow<List<DeeplinkEntity>>


    @Query("SELECT * FROM deeplink  ORDER by createAt desc")
    fun all(): Flow<List<DeeplinkEntity>>

    @Query("DELETE FROM deeplink")
    fun deleteAll()

}