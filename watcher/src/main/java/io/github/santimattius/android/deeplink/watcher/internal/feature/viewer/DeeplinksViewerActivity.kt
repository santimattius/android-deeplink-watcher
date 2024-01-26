package io.github.santimattius.android.deeplink.watcher.internal.feature.viewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.AppBar
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.AppBarIconModel
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.DeeplinkWatcherContainer

class DeeplinksViewerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeeplinkWatcherContainer {
                DeeplinkViewerScreen()
            }
        }
    }
}

@Composable
fun DeeplinkViewerScreen() {
    Scaffold(
        topBar = {
            AppBar(
                title = "Deeplink Viewer",
                navIcon = AppBarIconModel(
                    icon = Icons.Default.Close,
                    contentDescription = "close",
                    action = {

                    }
                )
            )
        }
    ) { padding ->
        DeeplinkViewerContent(padding)
    }
}

@Composable
private fun DeeplinkViewerContent(
    padding: PaddingValues,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Column {
            SearchBar()
            DeeplinkViewColletion()
        }
    }
}

@Composable
private fun SearchBar() {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        value = "",
        onValueChange = {}
    )
}

@Composable
private fun DeeplinkViewColletion() {
    LazyColumn(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        items(10) {
            DeeplinkItem(it)
        }
    }
}

@Composable
private fun DeeplinkItem(it: Int) {
    Card(
        modifier = Modifier.padding(top = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        ListItem(
            overlineContent = {
                Text(
                    text = "overlineContent",
                )
            },
            headlineContent = {
                Text(
                    text = "Hello $it!",
                )
            },
            supportingContent = {
                Text(
                    text = "supportingContent",
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
        DeeplinkViewerScreen()
    }
}