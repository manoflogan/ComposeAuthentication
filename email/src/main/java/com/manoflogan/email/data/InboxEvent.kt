package com.manoflogan.email.data

sealed interface InboxEvent {

    data object RefreshEvent: InboxEvent

    data class DeleteEvent(val id: String): InboxEvent
}