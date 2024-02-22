package io.github.santimattius.android.deeplink.watcher.internal.di

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.DeepLinksViewerViewModel

internal fun Activity.createDeepLinksViewerViewModel(): ViewModelProvider.Factory {
    val repository by lazy { DependencyProvider.provideDeeplinkRepository(this.application) }
    return viewModelFactory {
        addInitializer(DeepLinksViewerViewModel::class) {
            DeepLinksViewerViewModel(repository)
        }
    }
}