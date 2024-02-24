package io.github.santimattius.android.deeplink.watcher.internal.core.domain

import io.github.santimattius.android.deeplink.watcher.internal.core.data.DeeplinkRepository
import io.github.santimattius.android.deeplink.watcher.internal.core.event.EventBus

internal class DeepLinkRegister(
    private val repository: DeeplinkRepository,
    private val eventBus: EventBus,
) {

    suspend operator fun invoke(uri: String, referrer: String?) {
        val deeplink = Deeplink.create(uri, referrer)
        repository.insert(deeplink)
        eventBus.emit(deeplink.pullDomainEvents())
    }
}