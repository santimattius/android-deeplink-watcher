package io.github.santimattius.android.deeplink.watcher.internal.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.santimattius.android.deeplink.watcher.internal.core.data.DeeplinkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class DeepLinkDetailViewModel(
    private val deeplinkRepository: DeeplinkRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DeepLinkDetailUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    fun load() {
        val id: String = savedStateHandle["deeplink_id"] ?: return
        viewModelScope.launch {
            deeplinkRepository.findById(id).onSuccess { deepLink ->
                _uiState.update { it.copy(isLoading = false, deepLink = deepLink) }
            }.onFailure {
                _uiState.update { it.copy(isLoading = false, withError = true) }
            }
        }
    }
}