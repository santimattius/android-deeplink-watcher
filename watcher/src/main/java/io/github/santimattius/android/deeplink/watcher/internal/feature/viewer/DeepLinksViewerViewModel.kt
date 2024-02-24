package io.github.santimattius.android.deeplink.watcher.internal.feature.viewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.santimattius.android.deeplink.watcher.internal.core.data.DeeplinkRepository
import io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.components.SearchBarModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class DeepLinksViewerViewModel(
    private val deeplinkRepository: DeeplinkRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DeeplinkViewerUiState())
    val uiState = _uiState.asStateFlow()

    init {
        onSearch()
    }

    fun onSearch() = viewModelScope.launch {
        deeplinkRepository.search(_uiState.value.text.value).collect { links ->
            _uiState.update {
                it.copy(
                    isLoading = false,
                    data = links,
                )
            }
        }
    }

    fun onWritingSearch(text: String) {
        _uiState.update { it.copy(text = SearchBarModel(text)) }
        if (text.isEmpty()) {
            onSearch()
        }
    }
}