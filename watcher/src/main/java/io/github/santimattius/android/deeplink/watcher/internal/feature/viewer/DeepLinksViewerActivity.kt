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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.santimattius.android.deeplink.watcher.R
import io.github.santimattius.android.deeplink.watcher.annotations.ExcludeDeeplinkWatch
import io.github.santimattius.android.deeplink.watcher.internal.core.domain.Deeplink
import io.github.santimattius.android.deeplink.watcher.internal.core.extensions.format
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.AppBar
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.AppBarIconModel
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.DeeplinkWatcherContainer
import io.github.santimattius.android.deeplink.watcher.internal.di.createDeepLinksViewerViewModel
import io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.components.SearchBar
import java.util.Date

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
    Box(
        modifier = modifier
    ) {
        Column {
            SearchBar(
                model = state.text,
                label = "Ej. scheme://host/path",
                onTextChange = onTextChange,
                onImeAction = onSearch,
            )
            DeeplinkViewCollection(state.data)
        }
    }
}

@Composable
private fun DeeplinkViewCollection(data: List<Deeplink>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            )
    ) {
        items(data, key = { it.id }) { deeplink ->
            DeeplinkItem(deeplink)
        }
    }
}

@Composable
private fun DeeplinkItem(deeplink: Deeplink) {
    Card(
        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = Color.White
            ),
            headlineContent = {
                Text(
                    text = deeplink.uri,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            supportingContent = {
                Column {
                    Text(
                        text = "from: ${deeplink.referrer ?: "None"}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(text = deeplink.createAt.format())
                }
            },
            leadingContent = {
                Icon(
                    painter = painterResource(
                        id = if (deeplink.isWeb()) {
                            R.drawable.ic_browser
                        } else {
                            R.drawable.ic_phone
                        }
                    ),
                    contentDescription = null
                )

            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun DeeplinkItemPreview() {
    DeeplinkWatcherContainer {
        DeeplinkItem(
            deeplink = Deeplink(
                id = "",
                uri = "http://github.com/santimattius",
                referrer = "app-android://",
                createAt = Date()
            )
        )
    }
}