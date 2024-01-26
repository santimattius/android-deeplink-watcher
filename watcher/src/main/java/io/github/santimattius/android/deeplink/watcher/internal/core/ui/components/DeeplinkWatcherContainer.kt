package io.github.santimattius.android.deeplink.watcher.internal.core.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.theme.DeeplinkWatcherTheme

@Composable
fun DeeplinkWatcherContainer(content: @Composable () -> Unit) {
    DeeplinkWatcherTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
            content = content
        )
    }
}