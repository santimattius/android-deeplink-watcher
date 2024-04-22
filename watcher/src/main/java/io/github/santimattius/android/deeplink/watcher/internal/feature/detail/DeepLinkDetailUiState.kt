package io.github.santimattius.android.deeplink.watcher.internal.feature.detail

import io.github.santimattius.android.deeplink.watcher.internal.core.domain.Deeplink

internal data class DeepLinkDetailUiState(
    val isLoading: Boolean = false,
    val deepLink: Deeplink? = null,
    val withError: Boolean = false
) {

    val isDeepLinkAvailable: Boolean
        get() = !isLoading && !withError && deepLink != null
}
