package io.github.santimattius.android.deeplink.watcher.internal.core.event

import kotlinx.coroutines.CoroutineScope

internal interface EventBus {

    fun subscribe(scope: CoroutineScope, block: suspend (Event) -> Unit)

    suspend fun emit(event: Event)
    suspend fun emit(event: List<Event>)
}