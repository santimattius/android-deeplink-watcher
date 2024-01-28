package io.github.santimattius.android.deeplink.watcher.internal.core.data

import io.github.santimattius.android.deeplink.watcher.internal.core.database.DeeplinkDao
import io.github.santimattius.android.deeplink.watcher.internal.core.database.DeeplinkEntity
import io.github.santimattius.android.deeplink.watcher.internal.core.domain.Deeplink
import java.util.Date

class RoomDeeplinkLocalDataSource(
    private val dao: DeeplinkDao,
) : DeeplinkLocalDataSource {
    override suspend fun insert(deeplink: Deeplink) {
        val entity = DeeplinkEntity(
            uuid = deeplink.id,
            uri = deeplink.uri,
            referrer = deeplink.referrer,
            createAt = Date()
        )
        dao.insert(entity)
    }
}