package io.github.santimattius.android.deeplink.watcher.internal.di

import android.content.Context
import io.github.santimattius.android.deeplink.watcher.internal.core.data.DeeplinkLocalDataSource
import io.github.santimattius.android.deeplink.watcher.internal.core.data.DeeplinkRepository
import io.github.santimattius.android.deeplink.watcher.internal.core.data.RoomDeeplinkLocalDataSource
import io.github.santimattius.android.deeplink.watcher.internal.core.database.DeeplinkDataBase
import io.github.santimattius.android.deeplink.watcher.internal.core.domain.DeepLinkRegister
import io.github.santimattius.android.deeplink.watcher.internal.core.event.EventBus
import io.github.santimattius.android.deeplink.watcher.internal.core.event.FlowsEventBus
import io.github.santimattius.android.deeplink.watcher.internal.navigation.LocalNavigationController
import io.github.santimattius.android.deeplink.watcher.internal.navigation.NavigationController

internal object DependencyProvider {

    @Volatile
    private var database: DeeplinkDataBase? = null

    @Volatile
    private var navController: NavigationController? = null


    fun provideNavController(context: Context): NavigationController {
        synchronized(this) {
            return navController ?: createNavController(context.applicationContext)
        }
    }

    private fun createNavController(context: Context): NavigationController {
        val controller = LocalNavigationController(context)
        navController = controller
        return controller
    }

    fun provideDeepLinkRegister(context: Context): DeepLinkRegister {
        val repository = provideDeeplinkRepository(context)
        val eventBus = provideEventBus()
        return DeepLinkRegister(repository, eventBus)
    }

    fun provideDeeplinkRepository(context: Context): DeeplinkRepository {
        val dataSource = provideDeeplinkLocalDataSource(context.applicationContext)
        return DeeplinkRepository(dataSource)
    }

    private fun provideDeeplinkLocalDataSource(context: Context): DeeplinkLocalDataSource {
        val dataBase = provideDataBase(context)
        return RoomDeeplinkLocalDataSource(dataBase.deeplinkDao())
    }

    private fun provideDataBase(context: Context): DeeplinkDataBase {
        synchronized(this) {
            return database ?: createDatabase(context)
        }
    }

    private fun createDatabase(context: Context): DeeplinkDataBase {
        val db = DeeplinkDataBase.get(context.applicationContext)
        database = db
        return db
    }

    fun provideEventBus(): EventBus {
        return FlowsEventBus
    }
}
