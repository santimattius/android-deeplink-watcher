package io.github.santimattius.android.deeplink.watcher.internal.feature.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign


@Composable
fun DetailRow(
    label: String,
    text: String,
) {
    DetailText(
        text = label,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        textStyle = MaterialTheme.typography.titleMedium
    )
    DetailText(text = text)
}

@Composable
fun DetailText(
    text: String,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.bodyLarge,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor),
        text = text,
        textAlign = TextAlign.Center,
        style = textStyle
    )
}