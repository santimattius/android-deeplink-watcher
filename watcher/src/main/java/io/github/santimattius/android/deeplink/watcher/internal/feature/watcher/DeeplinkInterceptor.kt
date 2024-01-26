package io.github.santimattius.android.deeplink.watcher.internal.feature.watcher

import android.app.Activity
import android.net.Uri
import io.github.santimattius.android.deeplink.watcher.application.DefaultActivityLifecycleCallbacks
import io.github.santimattius.android.deeplink.watcher.application.ExcludeDeeplinkWatch

internal class DeeplinkInterceptor : DefaultActivityLifecycleCallbacks {

    override fun onActivityStarted(activity: Activity) {
        if (ensureWatchDeeplink(activity)) return

        val referrer: Uri? = activity.referrer
        val deeplink: Uri? = activity.intent.data

        saveDeeplinkData(deeplink, referrer)
    }

    private fun ensureWatchDeeplink(activity: Activity): Boolean {
        return activity::class.annotations.any { it.annotationClass == ExcludeDeeplinkWatch::annotationClass }
    }

    private fun saveDeeplinkData(deeplink: Uri?, referrer: Uri?) {

    }
}