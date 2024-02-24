package io.github.santimattius.android.deeplink.watcher.internal.core.extensions

import android.util.Patterns

internal fun String.isUrl(): Boolean {
    return Patterns.WEB_URL.matcher(this).matches()
}