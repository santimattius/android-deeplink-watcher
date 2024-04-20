package io.github.santimattius.android.deeplink.watcher.internal.core.data

import io.github.santimattius.android.deeplink.watcher.internal.core.domain.Deeplink
import kotlinx.coroutines.flow.Flow

internal interface DeeplinkLocalDataSource {
    suspend fun insert(deeplink: Deeplink)
    fun search(text: String): Flow<List<Deeplink>>
    suspend fun delete(deeplink: Deeplink)
    suspend fun deleteAll()

    suspend fun findById(id: String): Result<Deeplink>
}
