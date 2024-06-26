package io.github.santimattius.android.deeplink.watcher.internal.feature.viewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.santimattius.android.deeplink.watcher.internal.core.data.DeeplinkRepository
import io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.components.DeeplinkViewCollectionAction
import io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.components.SearchBarModel
import io.github.santimattius.android.deeplink.watcher.internal.navigation.NavigationController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class DeepLinksViewerViewModel(
    private val deeplinkRepository: DeeplinkRepository,
    private val navController: NavigationController,
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

    fun onItemAction(action: DeeplinkViewCollectionAction) {
        when (action) {
            is DeeplinkViewCollectionAction.Clicked -> {
                navController.goToDetail(action.deeplink.id)
            }

            is DeeplinkViewCollectionAction.Deleted -> {
                viewModelScope.launch {
                    deeplinkRepository.delete(action.deeplink)
                }
            }

            DeeplinkViewCollectionAction.DeleteAll -> {
                viewModelScope.launch {
                    deeplinkRepository.deleteAll()
                }
            }
        }
    }
}