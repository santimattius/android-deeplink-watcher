package io.github.santimattius.android.deeplink.watcher.internal.core.domain

import io.github.santimattius.android.deeplink.watcher.internal.core.event.Event
import java.util.UUID

internal class DeepLinkCreatedEvent(
    val uri: String,
    val from: String?
) : Event {
    override val id: String
        get() = UUID.randomUUID().toString()
}