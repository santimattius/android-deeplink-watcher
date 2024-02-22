package io.github.santimattius.android.deeplink.watcher.internal.feature.watcher

import android.app.Activity
import android.net.Uri
import io.github.santimattius.android.deeplink.watcher.annotations.ExcludeDeeplinkWatch
import io.github.santimattius.android.deeplink.watcher.internal.core.data.DeeplinkRepository
import io.github.santimattius.android.deeplink.watcher.internal.core.domain.Deeplink
import io.github.santimattius.android.deeplink.watcher.internal.core.lifecycle.DefaultActivityLifecycleCallbacks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

internal class DeeplinkInterceptor(
    private val repository: DeeplinkRepository,
) : DefaultActivityLifecycleCallbacks {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onActivityStarted(activity: Activity) {
        if (ensureWatchDeeplink(activity)) return

        val referrer: Uri? = activity.referrer
        val deeplink: Uri? = activity.intent.data

        saveDeeplinkData(deeplink, referrer)
    }

    private fun ensureWatchDeeplink(activity: Activity): Boolean {
        return activity::class.annotations.any { it.annotationClass == ExcludeDeeplinkWatch::annotationClass }
    }

    private fun saveDeeplinkData(uri: Uri?, referrer: Uri?) {
        if (uri == null) return
        val deeplink = Deeplink.create(uri.toString(), referrer?.toString())
        coroutineScope.launch {
            repository.insert(deeplink)
        }
    }
}