package io.github.santimattius.android.deeplink.watcher.internal.core.domain

import io.github.santimattius.android.deeplink.watcher.internal.core.extensions.isUrl
import java.util.Date
import java.util.UUID

data class Deeplink(
    val id: String,
    val uri: String,
    val referrer: String?,
    val createAt: Date,
) {
    fun isWeb(): Boolean = uri.isUrl()

    companion object {

        fun create(uri: String, referrer: String? = null): Deeplink {
            val id = UUID.randomUUID().toString()
            return Deeplink(
                id = id,
                uri = uri,
                referrer = referrer,
                createAt = Date()
            )
        }
    }
}