package io.github.santimattius.android.deeplink.watcher.internal.feature.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.santimattius.android.deeplink.watcher.R
import io.github.santimattius.android.deeplink.watcher.annotations.ExcludeFromDeeplinkWatcher
import io.github.santimattius.android.deeplink.watcher.internal.core.domain.Deeplink
import io.github.santimattius.android.deeplink.watcher.internal.core.extensions.format
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.AppBar
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.AppBarIconModel
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.DeeplinkWatcherContainer
import io.github.santimattius.android.deeplink.watcher.internal.di.createDeepLinkDetailViewModel
import io.github.santimattius.android.deeplink.watcher.internal.feature.detail.components.DetailRow

@ExcludeFromDeeplinkWatcher
class DeepLinkDetailActivity : ComponentActivity() {

    private val viewModel: DeepLinkDetailViewModel by viewModels {
        createDeepLinkDetailViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeeplinkWatcherContainer {
                DeepLinkDetailScreen(viewModel = viewModel, onBack = { finish() })
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.load()
    }
}

@Composable
private fun DeepLinkDetailScreen(
    viewModel: DeepLinkDetailViewModel,
    onBack: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.title_deeplink_viewer),
                navIcon = AppBarIconModel(
                    icon = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(R.string.text_close_action),
                    action = onBack
                ),
                actions = {
                    val deepLink = state.deepLink
                    if (deepLink != null) {
                        IconButton(onClick = {  clipboardManager.setText(AnnotatedString(deepLink.uri))}) {
                            Icon(painter = painterResource(id = R.drawable.ic_copy), contentDescription =  stringResource(R.string.text_close_action))
                        }
                    }
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.isLoading -> CircularProgressIndicator()
                state.withError || state.deepLink == null -> Text(text = "Ocurrio un error")
                else -> {
                    val deepLink = state.deepLink!!
                    DeepLinkDetailContent(deepLink)
                }
            }
        }
    }
}

@Composable
private fun DeepLinkDetailContent(deeplink: Deeplink, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DetailRow(label = "CreateAt", text = deeplink.createAt.format())
            DetailRow(label = "From", text = deeplink.referrer ?: "Undefined")
            DetailRow(label = "Uri", text = deeplink.uri)
        }
    }
}