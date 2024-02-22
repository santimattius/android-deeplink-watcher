package io.github.santimattius.android.deeplink.watcher.internal.feature.viewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.santimattius.android.deeplink.watcher.annotations.ExcludeDeeplinkWatch
import io.github.santimattius.android.deeplink.watcher.internal.core.domain.Deeplink
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.AppBar
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.AppBarIconModel
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.DeeplinkWatcherContainer
import io.github.santimattius.android.deeplink.watcher.internal.di.createDeepLinksViewerViewModel
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
            onSearch = viewModel::onSearch
        )
    }
}

@Composable
private fun DeeplinkViewerContent(
    modifier: Modifier = Modifier,
    state: DeeplinkViewerUiState,
    onSearch: (String) -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        Column {
            SearchBar(
                model = state.text,
                onTextChange = onSearch
            )
            DeeplinkViewCollection(state.data)
        }
    }
}

@Composable
private fun DeeplinkViewCollection(data: List<Deeplink>) {
    LazyColumn(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        items(data, key = { it.id }) { deeplink ->
            DeeplinkItem(deeplink)
        }
    }
}

@Composable
private fun DeeplinkItem(deeplink: Deeplink) {
    Card(
        modifier = Modifier.padding(top = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = deeplink.uri,
                )
            },
            supportingContent = {
                Text(
                    text = deeplink.referrer ?: "None",
                )
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null
                )
            }
        )
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_3A)
@Composable
fun GreetingPreview() {
    DeeplinkWatcherContainer {
        //DeeplinkViewerScreen(viewModel)
    }
}