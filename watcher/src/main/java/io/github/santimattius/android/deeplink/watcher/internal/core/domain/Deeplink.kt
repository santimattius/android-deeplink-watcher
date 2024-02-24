package io.github.santimattius.android.deeplink.watcher.internal.core.domain

import io.github.santimattius.android.deeplink.watcher.internal.core.extensions.isUrl
import java.util.Date
import java.util.UUID

internal data class Deeplink(
    val id: String,
    val uri: String,
    val referrer: String?,
    val createAt: Date,
) : AggregateRoot() {
    fun isWeb(): Boolean = uri.isUrl()

    companion object {

        fun create(uri: String, referrer: String? = null): Deeplink {
            val id = UUID.randomUUID().toString()
            val deeplink = Deeplink(
                id = id,
                uri = uri,
                referrer = referrer,
                createAt = Date()
            ).apply {
                this.record(DeepLinkCreatedEvent(uri = uri, from = referrer))
            }
            return deeplink
        }
    }
}