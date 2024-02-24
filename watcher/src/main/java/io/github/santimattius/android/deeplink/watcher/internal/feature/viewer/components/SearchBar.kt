package io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Immutable
data class SearchBarModel(
    val value: String
)

@Composable
internal fun SearchBar(
    model: SearchBarModel,
    label: String = "",
    onTextChange: (String) -> Unit,
    enabled: Boolean = true,
    onImeAction: () -> Unit = {},
) {
    InputText(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        text = model.value,
        label = label,
        enabled = enabled,
        onTextChange = onTextChange,
        onImeAction = onImeAction
    )
}

@Composable
private fun InputText(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    label: String = "",
    enabled: Boolean = true,
    onImeAction: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        maxLines = 1,
        enabled = enabled,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onImeAction()
            keyboardController?.hide()
        }),
        modifier = modifier,
        placeholder = { Text(text = label) },
        trailingIcon = {
            IconButton(onClick = onImeAction) {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = "search")
            }
        }
    )
}