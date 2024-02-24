package com.santimattius.basic.skeleton

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import io.github.santimattius.android.deeplink.watcher.DeeplinkWatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class MainApplication : Application() {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        DeeplinkWatcher.watch(scope) {
            Log.d("Subscriber", "Deeplink captured: $it")
        }
    }
}