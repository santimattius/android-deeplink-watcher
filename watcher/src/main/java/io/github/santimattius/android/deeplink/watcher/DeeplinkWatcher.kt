package io.github.santimattius.android.deeplink.watcher

import android.content.Context
import io.github.santimattius.android.deeplink.watcher.internal.core.domain.DeepLinkCreatedEvent
import io.github.santimattius.android.deeplink.watcher.internal.di.DependencyProvider
import io.github.santimattius.android.deeplink.watcher.internal.initializer.DeeplinkWatcherInitializer
import kotlinx.coroutines.CoroutineScope

object DeeplinkWatcher {

    private val eventBus by lazy { DependencyProvider.provideEventBus() }

    @JvmStatic
    fun showViewer(context: Context) {
        val navController = DependencyProvider.provideNavController(context)
        navController.goToViewer()
    }

    @JvmStatic
    fun attach(context: Context) {
        val initializer = DeeplinkWatcherInitializer()
        initializer.create(context.applicationContext)
    }

    @JvmStatic
    fun watch(coroutineScope: CoroutineScope, block: suspend (DeepLinkInfo) -> Unit) {
        eventBus.subscribe(coroutineScope) { event ->
            if (event is DeepLinkCreatedEvent) {
                val info = DeepLinkInfo(event.uri, event.from)
                block(info)
            }
        }
    }
}