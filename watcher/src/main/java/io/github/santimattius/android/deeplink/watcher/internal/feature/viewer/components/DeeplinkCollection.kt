package io.github.santimattius.android.deeplink.watcher.internal.feature.viewer.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.santimattius.android.deeplink.watcher.R
import io.github.santimattius.android.deeplink.watcher.internal.core.domain.Deeplink
import io.github.santimattius.android.deeplink.watcher.internal.core.extensions.format
import io.github.santimattius.android.deeplink.watcher.internal.core.ui.components.DeeplinkWatcherContainer
import java.util.Date


@Composable
internal fun DeeplinkViewCollection(data: List<Deeplink>) {
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
            text = "No content available",
            style = MaterialTheme.typography.titleLarge
        )
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