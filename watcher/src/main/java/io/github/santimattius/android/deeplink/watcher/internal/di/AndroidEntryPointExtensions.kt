package io.github.santimattius.android.deeplink.watcher.internal.di

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.viewModelFactory
import io.github.santimattius.android.deeplink.watcher.internal.feature.detail.DeepLinkDetailViewModel
import io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.DeepLinksViewerViewModel

internal fun Activity.createDeepLinksViewerViewModel(): ViewModelProvider.Factory {
    val repository by lazy { DependencyProvider.provideDeeplinkRepository(this.application) }
    val navController by lazy { DependencyProvider.provideNavController(this.application) }
    return viewModelFactory {
        addInitializer(DeepLinksViewerViewModel::class) {
            DeepLinksViewerViewModel(repository, navController)
        }
    }
}

internal fun Activity.createDeepLinkDetailViewModel(): ViewModelProvider.Factory {
    val repository by lazy { DependencyProvider.provideDeeplinkRepository(this.application) }
    return viewModelFactory {
        addInitializer(DeepLinkDetailViewModel::class) {
            DeepLinkDetailViewModel(repository, this.createSavedStateHandle())
        }
    }
}