package io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.santimattius.android.deeplink.watcher.R
import io.github.santimattius.android.deeplink.watcher.internal.core.domain.Deeplink
import io.github.santimattius.android.deeplink.watcher.internal.core.extensions.format
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.CustomAnimatedVisibility
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.DeeplinkWatcherContainer
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.SwipeToDismissComponent
import java.util.Date

internal sealed interface DeeplinkViewCollectionAction {
    data object DeleteAll : DeeplinkViewCollectionAction

    data class Deleted(val deeplink: Deeplink) : DeeplinkViewCollectionAction
    data class Clicked(val deeplink: Deeplink) : DeeplinkViewCollectionAction
}

@Composable
internal fun DeeplinkViewCollection(
    data: List<Deeplink>,
    onAction: (DeeplinkViewCollectionAction) -> Unit = {}
) {
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
            DeeplinkCard(
                deeplink = deeplink,
                onItemClick = { onAction(DeeplinkViewCollectionAction.Clicked(it)) },
                onItemDelete = { onAction(DeeplinkViewCollectionAction.Deleted(it)) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeeplinkCard(
    deeplink: Deeplink,
    onItemClick: (Deeplink) -> Unit,
    onItemDelete: (Deeplink) -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState()
    val isDismissed = dismissState.currentValue == SwipeToDismissBoxValue.EndToStart
    if (isDismissed) {
        onItemDelete(deeplink)
    }
    CustomAnimatedVisibility(visible = isDismissed) {
        SwipeToDismissComponent(dismissState = dismissState) {
            DeeplinkItem(deeplink = deeplink, onClick = onItemClick)
        }
    }
}


@Composable
internal fun EmptyDeeplinkCollection() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(56.dp),
            painter = painterResource(id = R.drawable.ic_browser),
            contentDescription = ""
        )
        Text(
            text = stringResource(R.string.text_content_not_available),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun DeeplinkItem(
    deeplink: Deeplink,
    onClick: (Deeplink) -> Unit,
) {
    Card(
        modifier = Modifier
            .clickable { onClick(deeplink) }
            .padding(top = 4.dp, bottom = 4.dp),
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
            ),
            onClick = {}
        )
    }
}