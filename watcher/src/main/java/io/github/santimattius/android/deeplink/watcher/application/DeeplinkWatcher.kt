package io.github.santimattius.android.deeplink.watcher.application

import android.content.Context
import android.content.Intent
import io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.DeeplinksViewerActivity
import io.github.santimattius.android.deeplink.watcher.internal.initializer.DeeplinkWatcherInitializer

object DeeplinkWatcher {

    @JvmStatic
    fun showViewer(context: Context) {
        val intent = Intent(context, DeeplinksViewerActivity::class.java)
        context.startActivity(intent)
    }

    @JvmStatic
    fun attach(context: Context) {
        val initializer = DeeplinkWatcherInitializer()
        initializer.create(context.applicationContext)
    }
}