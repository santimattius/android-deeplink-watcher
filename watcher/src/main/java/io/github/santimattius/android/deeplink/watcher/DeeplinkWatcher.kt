package io.github.santimattius.android.deeplink.watcher

import android.content.Context
import android.content.Intent
import io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.DeepLinksViewerActivity
import io.github.santimattius.android.deeplink.watcher.internal.initializer.DeeplinkWatcherInitializer

object DeeplinkWatcher {

    @JvmStatic
    fun showViewer(context: Context) {
        val intent = Intent(context, DeepLinksViewerActivity::class.java)
        context.startActivity(intent)
    }

    @JvmStatic
    fun attach(context: Context) {
        val initializer = DeeplinkWatcherInitializer()
        initializer.create(context.applicationContext)
    }
}