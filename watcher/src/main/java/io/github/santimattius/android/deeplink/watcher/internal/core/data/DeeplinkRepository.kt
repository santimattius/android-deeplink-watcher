package io.github.santimattius.android.deeplink.watcher.internal.core.data

import io.github.santimattius.android.deeplink.watcher.internal.core.domain.Deeplink

internal class DeeplinkRepository(
    private val deeplinkLocalDataSource: DeeplinkLocalDataSource,
) {

    suspend fun insert(deeplink: Deeplink){
        deeplinkLocalDataSource.insert(deeplink)
    }
}