package io.github.santimattius.android.deeplink.watcher.internal.core.data

import io.github.santimattius.android.deeplink.watcher.internal.core.domain.Deeplink
import kotlinx.coroutines.flow.Flow

internal class DeeplinkRepository(
    private val deeplinkLocalDataSource: DeeplinkLocalDataSource,
) {

    suspend fun insert(deeplink: Deeplink) {
        deeplinkLocalDataSource.insert(deeplink)
    }

    fun search(text: String): Flow<List<Deeplink>> {
        return deeplinkLocalDataSource.search(text)
    }

    suspend fun delete(deeplink: Deeplink) {
        deeplinkLocalDataSource.delete(deeplink)
    }

    suspend fun deleteAll() {
        deeplinkLocalDataSource.deleteAll()
    }
}