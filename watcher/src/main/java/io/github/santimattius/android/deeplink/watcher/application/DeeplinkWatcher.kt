package io.github.santimattius.android.deeplink.watcher.application

import android.content.Context
import android.content.Intent
import io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.DeeplinksViewerActivity

object DeeplinkWatcher {

    @JvmStatic
    fun showViewer(context: Context) {
        val intent = Intent(context, DeeplinksViewerActivity::class.java)
        context.startActivity(intent)
    }
}