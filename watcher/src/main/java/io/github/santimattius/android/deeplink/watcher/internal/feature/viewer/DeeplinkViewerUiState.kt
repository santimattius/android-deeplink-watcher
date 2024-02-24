package io.github.santimattius.android.deeplink.watcher.internal.feature.viewer

import androidx.compose.runtime.Stable
import io.github.santimattius.android.deeplink.watcher.internal.core.domain.Deeplink
import io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.components.SearchBarModel

@Stable
internal data class DeeplinkViewerUiState(
    val isLoading: Boolean = true,
    val text: SearchBarModel = SearchBarModel(""),
    val data: List<Deeplink> = emptyList()
)
