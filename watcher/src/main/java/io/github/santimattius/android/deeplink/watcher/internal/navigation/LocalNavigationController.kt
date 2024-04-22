package io.github.santimattius.android.deeplink.watcher.internal.navigation

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import io.github.santimattius.android.deeplink.watcher.internal.feature.detail.DeepLinkDetailActivity
import io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.DeepLinksViewerActivity

internal class LocalNavigationController(
    private val context: Context
) : NavigationController {
    override fun goToDetail(id: String) {
        val intent = Intent(context, DeepLinkDetailActivity::class.java).apply {
            putExtra("deeplink_id", id)
        }
        intent.flags = FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    override fun goToViewer() {
        val intent = Intent(context, DeepLinksViewerActivity::class.java)
        intent.flags = FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}