package io.github.santimattius.android.deeplink.watcher.internal.di

import android.app.Application
import android.content.Context
import io.github.santimattius.android.deeplink.watcher.internal.core.data.DeeplinkLocalDataSource
import io.github.santimattius.android.deeplink.watcher.internal.core.data.DeeplinkRepository
import io.github.santimattius.android.deeplink.watcher.internal.core.data.RoomDeeplinkLocalDataSource
import io.github.santimattius.android.deeplink.watcher.internal.core.database.DeeplinkDataBase

internal object DependencyProvider {

    @Volatile
    private var database: DeeplinkDataBase? = null

    fun provideDeeplinkRepository(context: Application): DeeplinkRepository {
        val dataSource = provideDeeplinkLocalDataSource(context)
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
}
