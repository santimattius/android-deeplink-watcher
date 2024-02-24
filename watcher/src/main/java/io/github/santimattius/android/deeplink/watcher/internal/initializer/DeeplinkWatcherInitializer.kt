package io.github.santimattius.android.deeplink.watcher.internal.initializer

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import io.github.santimattius.android.deeplink.watcher.internal.di.DependencyProvider
import io.github.santimattius.android.deeplink.watcher.internal.feature.watcher.DeeplinkInterceptor

class DeeplinkWatcherInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        if (context is Application) {
            val deepLinkRegister = DependencyProvider.provideDeepLinkRegister(context)
            val interceptor = DeeplinkInterceptor(deepLinkRegister)
            context.registerActivityLifecycleCallbacks(interceptor)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}