package io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Immutable
data class SearchBarModel(
    val text: String
)

@Composable
internal fun SearchBar(
    model: SearchBarModel,
    onTextChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        value = model.text,
        onValueChange = onTextChange
    )
}