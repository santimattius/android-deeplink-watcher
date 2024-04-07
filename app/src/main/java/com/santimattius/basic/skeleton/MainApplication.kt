package com.santimattius.basic.skeleton

import android.app.Application
import android.util.Log
import io.github.santimattius.android.deeplink.watcher.DeeplinkWatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MainApplication : Application() {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        DeeplinkWatcher.watch(scope) {
            Log.d("Subscriber", "Deeplink captured: $it")
        }
    }
}