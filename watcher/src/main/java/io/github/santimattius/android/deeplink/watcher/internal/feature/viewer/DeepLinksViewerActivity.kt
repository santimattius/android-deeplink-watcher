package io.github.santimattius.android.deeplink.watcher.internal.feature.viewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.santimattius.android.deeplink.watcher.R
import io.github.santimattius.android.deeplink.watcher.annotations.ExcludeDeeplinkWatch
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.AppBar
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.AppBarIconModel
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.DeeplinkWatcherContainer
import io.github.santimattius.android.deeplink.watcher.internal.di.createDeepLinksViewerViewModel
import io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.components.DeeplinkViewCollection
import io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.components.EmptyDeeplinkCollection
import io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.components.SearchBar

@ExcludeDeeplinkWatch
class DeepLinksViewerActivity : ComponentActivity() {

    private val viewModel: DeepLinksViewerViewModel by viewModels {
        createDeepLinksViewerViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeeplinkWatcherContainer {
                DeeplinkViewerScreen(
                    viewModel = viewModel,
                    onClose = {
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
private fun DeeplinkViewerScreen(
    viewModel: DeepLinksViewerViewModel,
    onClose: () -> Unit,
) {
    Scaffold(
        topBar = {
            AppBar(
                title = "Deeplink Viewer",
                navIcon = AppBarIconModel(
                    icon = Icons.Default.Close,
                    contentDescription = "close",
                    action = onClose
                )
            )
        }
    ) { padding ->

        val state by viewModel.uiState.collectAsStateWithLifecycle()
        DeeplinkViewerContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            state = state,
            onTextChange = viewModel::onWritingSearch,
            onSearch = viewModel::onSearch
        )
    }
}

@Composable
private fun DeeplinkViewerContent(
    modifier: Modifier = Modifier,
    state: DeeplinkViewerUiState,
    onTextChange: (String) -> Unit,
    onSearch: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        val isEmpty = state.data.isEmpty()
        SearchBar(
            model = state.text,
            label = stringResource(R.string.text_ej_search_query),
            enabled = !isEmpty,
            onTextChange = onTextChange,
            onImeAction = onSearch,
        )
        if (isEmpty) {
            EmptyDeeplinkCollection()
        } else {
            DeeplinkViewCollection(state.data)
        }
    }
}


@Preview
@Composable
fun DeeplinkContentPreview() {
    DeeplinkWatcherContainer {
        DeeplinkViewerContent(
            state = DeeplinkViewerUiState(),
            onTextChange = {},
            onSearch = {}
        )
    }
}