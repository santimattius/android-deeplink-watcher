package io.github.santimattius.android.deeplink.watcher.internal.core.data

import io.github.santimattius.android.deeplink.watcher.internal.core.database.DeeplinkDao
import io.github.santimattius.android.deeplink.watcher.internal.core.database.DeeplinkEntity
import io.github.santimattius.android.deeplink.watcher.internal.core.domain.Deeplink
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.Date

internal class RoomDeeplinkLocalDataSource(
    private val dao: DeeplinkDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : DeeplinkLocalDataSource {
    override suspend fun insert(deeplink: Deeplink) {
        val entity = DeeplinkEntity(
            uuid = deeplink.id,
            uri = deeplink.uri,
            referrer = deeplink.referrer,
            createAt = Date()
        )
        withContext(dispatcher) {
            dao.insert(entity)
        }
    }

    override fun search(text: String): Flow<List<Deeplink>> {
        return if (text.isBlank()) {
            dao.all()
        } else {
            dao.search(text)
        }.map { links ->
            links.map {
                Deeplink(
                    id = it.uuid,
                    uri = it.uri,
                    referrer = it.referrer,
                    createAt = it.createAt
                )
            }
        }
    }

    override suspend fun delete(deeplink: Deeplink) {
        withContext(dispatcher) {
            dao.delete(deeplink.id)
        }
    }

    override suspend fun deleteAll() {
        withContext(dispatcher) { dao.deleteAll() }
    }
}