package io.github.santimattius.android.deeplink.watcher.internal.initializer

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import io.github.santimattius.android.deeplink.watcher.internal.core.data.DeeplinkRepository
import io.github.santimattius.android.deeplink.watcher.internal.di.DependencyProvider
import io.github.santimattius.android.deeplink.watcher.internal.feature.watcher.DeeplinkInterceptor

class DeeplinkWatcherInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        if (context is Application) {
            val repository: DeeplinkRepository =
                DependencyProvider.provideDeeplinkRepository(context)
            val interceptor = DeeplinkInterceptor(repository)
            context.registerActivityLifecycleCallbacks(interceptor)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}