package com.manoflogan.email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manoflogan.email.data.InboxEvent
import com.manoflogan.email.data.InboxStatus
import com.manoflogan.email.util.makeEmailList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InboxViewModel: ViewModel() {

    private val _inboxStatusStateFlow: MutableStateFlow<InboxStatus> = MutableStateFlow(InboxStatus.Loading)
    val inboxStatusStateFlow: StateFlow<InboxStatus> = _inboxStatusStateFlow.asStateFlow()

    private fun loadInbox() {
        viewModelScope.launch {
            _inboxStatusStateFlow.value = InboxStatus.Loading
            delay(2_000)
            _inboxStatusStateFlow.value = InboxStatus.HasEmails(makeEmailList())
        }
    }

    fun deleteContent(id: String) {
        _inboxStatusStateFlow.update { inboxStatus ->
            when {
                (inboxStatus is InboxStatus.HasEmails) -> {
                    val filteredList = inboxStatus.emails.filterNot { email ->
                        email.id == id
                    }
                    InboxStatus.HasEmails(filteredList)
                }
                else -> inboxStatus
            }
        }
    }

    fun handleContentEvent(inboxEvent: InboxEvent) {
        when(inboxEvent) {
            InboxEvent.RefreshEvent -> loadInbox()
            is InboxEvent.DeleteEvent -> deleteContent(inboxEvent.id)
        }
    }
}