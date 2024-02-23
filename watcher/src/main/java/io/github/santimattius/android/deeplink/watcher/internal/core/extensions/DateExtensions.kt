package io.github.santimattius.android.deeplink.watcher.internal.core.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal fun Date.format(): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
    return formatter.format(this)
}