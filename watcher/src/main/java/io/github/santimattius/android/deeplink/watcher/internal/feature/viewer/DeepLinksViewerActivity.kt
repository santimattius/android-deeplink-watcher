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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.santimattius.android.deeplink.watcher.R
import io.github.santimattius.android.deeplink.watcher.annotations.ExcludeFromDeeplinkWatcher
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.AppBar
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.AppBarIcon
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.AppBarIconModel
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.DeeplinkWatcherContainer
import io.github.santimattius.android.deeplink.watcher.internal.di.createDeepLinksViewerViewModel
import io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.components.DeeplinkViewCollection
import io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.components.DeeplinkViewCollectionAction
import io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.components.EmptyDeeplinkCollection
import io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.components.SearchBar

@ExcludeFromDeeplinkWatcher
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
                title = stringResource(R.string.title_deeplink_viewer),
                navIcon = AppBarIconModel(
                    icon = Icons.Default.Close,
                    contentDescription = stringResource(R.string.text_close_action),
                    action = onClose
                ),
                actions = {
                    AppBarIcon(
                        navIcon = AppBarIconModel(
                            icon = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.text_close_action),
                            action = { viewModel.onItemAction(DeeplinkViewCollectionAction.DeleteAll) }
                        )
                    )
                }
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
            onSearch = viewModel::onSearch,
            onViewCollectionAction = viewModel::onItemAction
        )
    }
}

@Composable
private fun DeeplinkViewerContent(
    modifier: Modifier = Modifier,
    state: DeeplinkViewerUiState,
    onTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    onViewCollectionAction: (DeeplinkViewCollectionAction) -> Unit
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
            DeeplinkViewCollection(
                data = state.data,
                onAction = onViewCollectionAction
            )
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
            onSearch = {},
            onViewCollectionAction = {}
        )
    }
}