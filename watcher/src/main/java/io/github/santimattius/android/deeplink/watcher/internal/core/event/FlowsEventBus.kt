package io.github.santimattius.android.deeplink.watcher.internal.core.event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal object FlowsEventBus : EventBus {

    private val _eventFlow = MutableSharedFlow<Event>()

    override fun subscribe(scope: CoroutineScope, block: suspend (Event) -> Unit) {
        _eventFlow.onEach(block).launchIn(scope)
    }

    override suspend fun emit(event: Event) {
        _eventFlow.tryEmit(event)
    }

    override suspend fun emit(event: List<Event>) {
        _eventFlow.emitAll(event.asFlow())
    }
}