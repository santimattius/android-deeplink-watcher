package io.github.santimattius.android.deeplink.watcher.internal.feature.watcher

import android.app.Activity
import android.net.Uri
import android.util.Log
import io.github.santimattius.android.deeplink.watcher.annotations.ExcludeFromDeeplinkWatcher
import io.github.santimattius.android.deeplink.watcher.internal.core.domain.DeepLinkRegister
import io.github.santimattius.android.deeplink.watcher.internal.core.lifecycle.DefaultActivityLifecycleCallbacks
import io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.DeepLinksViewerActivity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

internal class DeeplinkInterceptor(
    private val deepLinkRegister: DeepLinkRegister,
) : DefaultActivityLifecycleCallbacks {

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(this::class.simpleName, "UnHandledException: ${throwable.message}", throwable)
    }

    override fun onActivityStarted(activity: Activity) {
        if (ensureWatchDeeplink(activity)) return

        val referrer: Uri? = activity.referrer
        val deeplink: Uri? = activity.intent.data

        saveDeeplinkData(deeplink, referrer)
    }

    private fun ensureWatchDeeplink(activity: Activity): Boolean {
        if (activity is DeepLinksViewerActivity) return true
        return activity::class.annotations.firstOrNull { it.annotationClass == ExcludeFromDeeplinkWatcher::class } != null
    }

    private fun saveDeeplinkData(uri: Uri?, referrer: Uri?) {
        if (uri == null) return
        coroutineScope.launch(exceptionHandler) {
            deepLinkRegister(uri.toString(), referrer?.toString())
        }
    }
}