package io.github.santimattius.android.deeplink.watcher.internal.core.data

import io.github.santimattius.android.deeplink.watcher.internal.core.domain.Deeplink

internal interface DeeplinkLocalDataSource {
    suspend fun insert(deeplink: Deeplink)

}
