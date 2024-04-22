package io.github.santimattius.android.deeplink.watcher.internal.navigation

internal interface NavigationController {

    fun goToDetail(id: String)

    fun goToViewer()
}