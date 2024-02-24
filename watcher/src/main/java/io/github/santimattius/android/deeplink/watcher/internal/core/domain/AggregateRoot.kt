package io.github.santimattius.android.deeplink.watcher.internal.core.domain

import io.github.santimattius.android.deeplink.watcher.internal.core.event.Event

internal abstract class AggregateRoot {

    private var events = mutableListOf<Event>()

    fun pullDomainEvents(): List<Event> {
        val currentEvents = this.events
        this.events = mutableListOf()
        return currentEvents
    }

    fun record(event: Event) {
        this.events.add(event)
    }

}