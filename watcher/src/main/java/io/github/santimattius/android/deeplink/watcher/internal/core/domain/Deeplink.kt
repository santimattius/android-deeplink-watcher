package io.github.santimattius.android.deeplink.watcher.internal.core.domain

import java.util.UUID

data class Deeplink(
    val id: String,
    val uri: String,
    val referrer: String?,
) {

    companion object {

        fun create(uri: String, referrer: String? = null): Deeplink {
            val id = UUID.randomUUID().toString()
            return Deeplink(
                id = id,
                uri = uri,
                referrer = referrer
            )
        }
    }
}